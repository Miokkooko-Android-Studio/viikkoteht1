Datamalli koostuu task-data classista. Data classin mukana tulee automaattinen toString, equals, hashCode ja copy. Luokan muuttujia ovat id: Int, title: String, description: String, priority: Int, dueDate: String, done: Boolean.
Funktioita on neljä, ja ne löytyvät Functions.kt tiedostosta. 
On addTask, joka lisää task listaan uuden taskin. 
On toggleDone, jonka avulla voi merkitä taskin tehdyksi. 
On filterByDone, jonka avulla voi filteröidä task listan sen perusteella onko se tehty tai ei. 
Sitten on vielä sortByDueDate, jonka avulla taskit voi lajitella päivämäärän mukaan. 

Voit testata tätä avaamalla sen android studiossa. 
