Action | Format, Examples                                                                                                                  
--------|-----------------------------------------------------------------------------------------------------------------------------------
**Help** | `help`                                                                                                                            
**List** | `list`                                                                                                                            
**Add** | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [t/TAG] [proj/PROJECT]…​` <br> e.g., `add n/James Ho p/91234567 e/jamesho@example.com t/friend p/project-work` 
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                             
**Find** | `find NAME [NAME]` or `find PHONE [PHONE]` <br> e.g., `find James Jake` or `find 87487765 88888888`                                         
**Delete** | `delete (p/PHONE \| INDEX)`<br> e.g., `delete 3` or `delete p/87487765`                                                                                              
**Tag**   | `tag p/PHONE (t/TAG \| proj/PROJECT) [t/TAG]…​ [proj/PROJECT]…​`<br> e.g., `tag p/91234567 t/bestie proj/project-x`      
**UnTag**   | `untag p/PHONE (t/TAG \| proj/PROJECT) [t/TAG]…​ [proj/PROJECT]…​`<br> e.g., `untag p/91234567 t/bestie proj/project-x`
**setstatus** | `setstatus INDEX proj/PROJECT [pay/PAYMENT] [by/DEADLINE] [prog/PROGRESS]` <br> e.g., `setstatus 1 proj/milestone1-5 pay/paid by/03 Apr 2025 2359 prog/complete`
**Save** | `save [FILENAME]` <br> e.g., `save newfile`                                                                                       
**Snapshot** | `snapshot`                                                                                                                        
**Switch Preferred Contact Method** | `switchcontact p/PHONE_NUMBER` <br> e.g, `switchcontact p/91234567`                                                               
**Clear** | `clear`                                                                                                                           
**Exit** | `exit`                                                                                                                              
For further information, go to: [https://ay2425s2-cs2103-f10-1.github.io/tp/UserGuide.html](https://ay2425s2-cs2103-f10-1.github.io/tp/UserGuide.html)

