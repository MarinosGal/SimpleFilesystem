package simple.filesystem.commands

import simple.filesystem.files.{DirEntry, File}
import simple.filesystem.filesystem.State

class Touch(name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path, name)
}

object Touch {
  val TOUCH = "touch"
}