package simple.filesystem.commands

import simple.filesystem.filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  def emptyCommand: Command = (state: State) => state
  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State = state.setMessage("name" + ": incomplete command")
  }

  def from(input: String): Command = {
    val tokens = input.split(" ")

    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else if(Mkdir.MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(Mkdir.MKDIR)
      else new Mkdir(tokens(1))
    } else if (Ls.LS.equals(tokens(0))) {
      new Ls
    } else if (Pwd.PWD.equals(tokens(0)))
      new Pwd
    else if (Touch.TOUCH.equals(tokens(0)))
      new Touch(tokens(1))
    else if (Cd.CD.equals(tokens(0)))
      if (tokens.length < 2) incompleteCommand(Cd.CD)
      else new Cd(tokens(1))
    else if (Rm.RM.equals(tokens(0)))
      if (tokens.length < 2) incompleteCommand(Rm.RM)
      else new Rm(tokens(1))
    else if (Echo.ECHO.equals(tokens(0)))
      if (tokens.length < 2) incompleteCommand(Echo.ECHO)
      else new Echo(tokens.tail)
    else if (Cat.CAT.equals(tokens(0)))
      if (tokens.length < 2) incompleteCommand(Cat.CAT)
      else new Cat(tokens(1))
    else
      new UnknownCommand
  }
}

/*
  Improvements/Additions
  1. add pattern matching to commands selection
  2. trait Command can be thought as a function: extends (State => State)
  3. Filesystem object: avoid var state
 */
