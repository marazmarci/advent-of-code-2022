import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * https://adventofcode.com/2022/day/7
 */
fun main() {
    val inputLines = readInputFileLines(7).filter { it.isNotBlank() }

    val root = DirectoryNode.ROOT
    var cwd = root

    inputLines.forEach { line ->
        when {
            line.startsWith("$ ") -> {
                val cmd = line.removePrefix("$ ")
                when {
                    cmd == "ls" -> {
                        // nothing
                    }

                    cmd.startsWith("cd") -> {
                        val targetDirName = cmd.removePrefix("cd ")
                        cwd = when (targetDirName) {
                            ".." -> cwd.parent!!
                            "/" -> root
                            else -> {
                                val child = cwd.children
                                    .filterIsInstance<DirectoryNode>()
                                    .filter { it.name == targetDirName }.let {
                                        check(it.size in 0..1)
                                        it.firstOrNull()
                                    }
                                child ?: cwd.addChildDirectory(targetDirName)
                            }
                        }
                    }

                    else -> error("unknown cmd: \"$cmd\"")
                }
            }

            line.startsWith("dir") -> {
                val dirName = line.removePrefix("dir ")
                cwd.addChildDirectory(dirName)
            }

            line.first().isDigit() -> {
                val (size, name) = line.split(' ')
                cwd.addChildFile(name, size.toInt())
            }

            else -> error("cannot parse line: \"$line\"")
        }
    }

    runBlocking {
        val sumSizeOfDirectoriesWithSizeAtMost100k = root.bfsFlow(includeRoot = true)
            .filterIsInstance<DirectoryNode>()
            .filter {
                it.size < 100_000
            }
            .toList()
            .sumOf {
                it.size
            }

        solution(1, sumSizeOfDirectoriesWithSizeAtMost100k, 1555642)
    }
}

private fun DirectoryNode.bfsFlow(includeRoot: Boolean = false) = flow {
    if (includeRoot) {
        emit(this@bfsFlow)
    }
    suspend fun DirectoryNode.emitAllNodes() {
        children.forEach {
            emit(it)
            if (it is DirectoryNode) {
                it.emitAllNodes()
            }
        }
    }
    emitAllNodes()
}

private sealed class FileSystemNode {
    abstract val name: String
    abstract val size: Int
    abstract val parent: DirectoryNode?
    val absolutePath: String
        get() = if (parent?.absolutePath == "/") {
            "/$name"
        } else {
            "${parent?.absolutePath ?: ""}/$name"
        }
}

private class DirectoryNode private constructor(
    override val name: String,
    override val parent: DirectoryNode?,
) : FileSystemNode() {
    private val mutableChildren = mutableListOf<FileSystemNode>()
    val children: List<FileSystemNode> get() = mutableChildren
    override val size get() = children.sumOf { it.size }

    fun addChildDirectory(name: String) = DirectoryNode(name, this).also {
        mutableChildren.add(it)
    }

    fun addChildFile(name: String, size: Int) = FileNode(name, size, this).also {
        mutableChildren.add(it)
    }

    companion object {
        val ROOT get() = DirectoryNode("", null)
    }
}

private class FileNode(
    override val name: String,
    override val size: Int,
    override val parent: DirectoryNode?,
) : FileSystemNode()
