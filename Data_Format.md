# Data Message Standard Form
For LoRa devices we have standarized the format in which data is being transmitted between nodes. 

## Device types ids:
In order to identify each device type we provide an id for each one. 
| Device | Device ID |
| ------ | --------- |
| Minuet | 1         |
| Duet   | 2         |

## Message format for Reciever (LoRa Server)
This was done in order to also capture the node id of the server. Previously we were ignoring the node id. Having the node id becomes useful when trying to identify the network being assesed.
| Reciever Flag | Node ID | Network Data... |
| ------------- | ------- | --------------- |

### Example message for Reciever
The following message *RECIEVER, 99@98,98, -120* represents the following:
| Reciever Flag | Node ID | Network Data... |
| ------------- | ------- | --------------- |
| RECIEVER      | 99      | .....           |

## Message format for Minuet and Duet
| Node ID | Device Type | pm10 | pm25 | pm100 | pn03 | pn05 | pn10 | pn25 | pn50 | pn100 | temp | humidity |
| ------- | ----------- | ---- | ---- | ----- | ---- | ---- | ---- | ---- | ---- | ----- | ---- | -------- |

### Example message for Minuet
The following message *98, 1, 1, 1, 1, 252, 59, 8, 0, 0, 0, 30, 60* represents the following:
| Node ID | Device Type | pm10 | pm25 | pm100 | pn03 | pn05 | pn10 | pn25 | pn50 | pn100 | temp | humidity |
| ------- | ----------- | ---- | ---- | ----- | ---- | ---- | ---- | ---- | ---- | ----- | ---- | -------- |
| 98      | 1           | 1    | 1    | 1     | 252  | 59   | 8    | 0    | 0    | 0     | 30   | 60       |

### Message format for Duet
The following message *98, 2, 1_2, 1_1, 1_3, 252_250, 59_50, 8_9, 0_0, 0_0, 0_0, 30, 60* represents the following:
| Node ID | Device Type | pm1 | pm2.5 | pm10 | pn0.3   | pn0.5  | pn1  | pn2.5 | pn5 | pn10 | temp | humidity |
| ------- | ----------- | --- | ----- | ---- | ------- | ------ | ---- | ----- | --- | ---- | ---- | -------- |
| 98      | 2           | 1 2 | 1 1   | 1  3 | 252 250 | 59  50 | 8  9 | 0  0  | 0 0 | 0  0 | 30   | 60       |

Note that the for the plant tower data the first value represents plant tower top and the second one represents plant tower bottom.

## Network data
For all devices each message should contain a delimiter to separate what is sensor data and what is network data. We are using the character *@* to denote this difference. 

### Example of data message
The following message 98, 1, 1, 1, 1, 252, 59, 8, 0, 0, 0, 30, 60 *@,98,99,-120* represents the following:
| From Node | To Node | Signal Strenght |
| --------- | ------- | --------------- |
| 98        | 99      | -120            |

Note that the network data can be as long as the data buffer in the subject device allows it to be.
