package simple.filesystem.commands

import simple.filesystem.files.{DirEntry, Directory}
import simple.filesystem.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.wd.path, name)
}

object Mkdir {
  val MKDIR = "mkdir"
}