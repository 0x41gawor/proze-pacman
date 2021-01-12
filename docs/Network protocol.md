

# Network protocol

Our protocol defines 3 types of messages that client can send to server:

- GET - to download configuration or level parameters from server.
- PUSH - to update the scoreboard
- PULL - to download the scoreboard

## GET

### Syntax

`GET <config_or_level> <item>`

**`<config_or_level>` possible values are:**

- CONFIG

  Then <item> specifies specific value stored in "config.properties" e.g LIVES, MAP_SIZE_X

- LEVEL

  Then <item> specifies the number of level e.g 1, 2, 3, 4 ...

### Examples

- GET CONFIG LIVES
- GET CONFIG MAP_SIZE_X
- GET LEVEL 1

### Response

**String** representing demanded item.

## PUSH

### Syntax

`PUSH <nickname> <score>`

### Examples

- PUSH JANUSZ 500
- PUSH AREK 500
- PUSH KUBA 500

### Response

**String** that says 'ok'.

## PULL

### Syntax

`PULL`

### Examples

- `PULL`

### Response

**JSON String** representing scoreboard.

