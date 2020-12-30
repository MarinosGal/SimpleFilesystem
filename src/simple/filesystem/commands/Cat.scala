package simple.filesystem.commands

import simple.filesystem.filesystem.State

class Cat(fileName: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd

    val dirEntry = wd.findEntry(fileName)
    if (dirEntry == null || !dirEntry.isFile)
      state.setMessage(fileName + ": no such file")
    else
      state.setMessage(dirEntry.asFile.contents)
  }
}

object Cat {
  val CAT = "cat"
}
