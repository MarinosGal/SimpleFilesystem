package simple.filesystem.commands

import simple.filesystem.files.{DirEntry, Directory}
import simple.filesystem.filesystem.State

abstract class CreateEntry(name: String) extends Command {

  def checkIllegalName(name: String): Boolean = {
    name.contains(".")
  }

  def doCreateEntry(name: String, state: State): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.wd
    val fullPath = wd.path

    // 1. all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath
    // 2. update the new structure  with the new directory entry in the wd
    val newEntry: DirEntry = createSpecificEntry(state)
    //val newDirectory = Directory.empty(fullPath, name)
    // 3. update the whole directory structure starting from the root
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    // (the directory structure us IMMUTABLE)
    // 4. find new working directory INSTANCE given wd's full path in the NEW directory structure
    val newWd = newRoot.findDescendant(allDirsInPath).asDirectory

    State(newRoot, newWd)

  }

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage(s"Entry $name already exists!")
    } else if (name.contains(Directory.SEPARATOR)) {
      // mkdir -p something/somethingElse
      state.setMessage(s"$name must not contain separators!")
    } else if (checkIllegalName(name)) {
      state.setMessage(s"$name illegal entry name!")
    } else {
      doCreateEntry(name, state)
    }
  }

  def createSpecificEntry(state: State): DirEntry
}
