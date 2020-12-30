package simple.filesystem.commands

import simple.filesystem.filesystem.State

class Pwd extends Command {

  override def apply(state: State): State =
    state.setMessage(state.wd.path)
}

object Pwd{
  val PWD = "pwd"
}
