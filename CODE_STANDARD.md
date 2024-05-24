# SOFTWARE CODE STANDARD

## Naming

### Naming of Variables

The name of Variables are nouns

Names of Variables should be formatted in this way:

> Example: user_id

### Naming of Functions

Functions should be named as verbs. (save, update, set, ...)

Formatting of them should be in CamelCase:

> Example: setUserID

(only the word "id" you write ID in full uppercase)

### Naming of Constants

Constants should be named like nouns. (SYSTEM_ID, VERSION, ...)

> Example: SYSTEM_ID

## Codestructure

### if/else

If else blocks should be written like this if possible. (Do not make an if in an if in an if)

> if (something is wrong) {
> throw exeption / return;
> }
> //continue code

### Standard Object Classes with Database Connection

if you have Classes that communicate with Databases your default class functions should be like:

- load
- save
- create
- delete
- special functions

Do not have any functions that communicate to the database except upper called functions
