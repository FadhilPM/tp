| **Action**|**Format**|**Example**|
|-------------------------------------|-------------------------------------|-------------------------------------|
| **Help**                            | `help`                                                     | —                                                                     |
| **List**                            | `list`                                                     | —                                                                     |
| **Add**                             | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [t/TAG]…​`            | `add n/James Ho p/91234567 e/jamesho@example.com t/friend t/colleague` |
| **Edit**                            | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]…​` | `edit 2 n/James Lee e/jameslee@example.com`                           |
| **Find**                            | `find KEYWORD [MORE_KEYWORDS]`                             | `find James Jake`<br>`find 87487765 88888888`                         |
| **Delete**                          | `delete INDEX`                                             | `delete 3`                                                            |
| **Tag**                             | `tag p/PHONE_NUMBER t/TAG [t/TAG]…​`                       | `tag p/91234567 t/project-x`                                          |
| **Untag**                           | `untag p/PHONE_NUMBER t/TAG [t/TAG]…​`                     | `untag p/91234567 t/project-x`                                        |
| **Set Status**                      | `setstatus INDEX proj/PROJECT [pay/PAYMENT] [by/DEADLINE] [prog/PROGRESS]`| `setstatus 1 proj/friend-project pay/paid by/05 Apr 2025 2359` |
| **Save**                            | `save [FILENAME]`                                          | `save newfile`                                                        |
| **Switch Preferred Contact Method** | `switchContact p/PHONE_NUMBER`                             | `switchContact p/91234567`                                            |
| **Clear**                           | `clear`                                                    | —                                                                     |
| **Exit**                            | `exit`                                                     | —                                                                     |


For further information, go to:

[https://ay2425s2-cs2103-f10-1.github.io/tp/UserGuide.html](https://ay2425s2-cs2103-f10-1.github.io/tp/UserGuide.html)
