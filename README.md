Viikkotehtävä 3:

MVVM on suunnittelumalli, joka erottaa sovelluksen eri vastuualueet: Model, View, sekä Viewmodel. MVVM on hyödyllinen, sillä se kannustaa puhdasta koodia, sekä mahdollistaa helpomman testauksen ja ylläpidon.

Stateflow on tapa säilyttää ja jakaa UI:n tai logiikan tämänhetkinen tila muille komponenteille. StateFlow sopii jatkuviin tiloihin, jossa arvot voivat muuttua, esimerkiksi kun dataa latautuu tai käyttäjä on kirjautumassa sisään. 
private val _uiState = MutableStateFlow(UiState())
val uiState: StateFlow<UiState> = _uiState
Tämän näköisellä koodilla yleisesti käytetään stateflowta. Ylempi muuttuja on sisäisesti muokattava ja alempi on vain luettava versio joka jaetaan ulospäin. Näin seuraajat eivät pääse vahingossa muokkaamaan tilaa. 
val state by viewModel.uiState.collectAsState()
UI kuuntelee tämän näköisellä koodilla tilaa. Aina kun state muuttuu compose tekee uudelleenpiirron automaattisesti. 

Viikkotehtävä 4:

Mitä tarkoittaa navigointi Jetpack Composessa:
Navigointi jetpack composessa tarkoittaa eri näyttöjen välillä siirtymistä sovelluksen sisällä. Se käyttää modernia navigointikirjastoa joka mahdollistaa reittien määrittelyn, sirtymisen ruusulta toiselle, sekä takaisin navigoinnin. 

Mitä ovat NavHost ja NavController:
NavHost on säiliö, joka näyttää nykyisen määränpään ja määrittelee navigaatiokaavion. NavController hallitsee navigointia NavHostissa ja tarjoaa metodit kuten navigate() ja popBackStack().

Miten sovelluksen navigaatiorakenne on toteutettu:
Sovellus alkaa HomeScreenistä, eli siitä näytöstä missä todo listaa voi selata ja muokata. HomeScreenistä löytyy ylänurkasta kaksi nappia. Kalenterin näköinen nappi navigoi CalenderScreeniin, jossa on todo lista kalenteri muodossa. Asetukset näppäimen näköinen painike navigoi SettingsScreenille, jonne tulee asetukset. Näistä kahdesta screenistä löytyy kummastakin ylänurkasta nuoli, josta palataan HomeScreeniin. 

Miten MVVM ja navigointi yhdistyvät ja miten tila jaetaan ruutujen välillä: 
Sovelluksesta löytyy taskViewModel, jossa löytyy listaus tehtävistä. Tämä tehtävälista pysyy tallennettuna taskViewModelissa, joka jaetaan eri ruutujen välille muokattavaksi. Kaikki tehdyt muutokset tulevat taskViewModeliin, jolloin kaikki ruudut jotka käyttävät sitä näkevät muutokset. Tila jaetaan ruutujen välillä parametrien kautta ylimmältä tasolta. 

Miten CalendarScreen on toteutettu: 
CalendarScreenissä näytetään taskit lähimmästä päivästä kaukaisimpaan. Ennen näyttämistä TaskList pistetään nousevaan järjestykseen ja ryhmitellään päivämäärän mukaan. Sitten ne näytetään siten, että päällä on taskien päivämäärä, ja alla on listaus tämän päivämäärän taskeista. 

Miten AlertDialog hoitaa addTask ja editTask: 
Kun HomeScreenissä painetaan joko nappia josta lisätään, tai editoidaan taskia, showEditDialog muuttuu trueksi, jolloin avautuu alertDialog DetailDialog tiedostosta. Siellä voi pistää Inputteihin halutut asiat ja painaa savea, jolloin showEditDialog = false, ja ruutu sulkeutuu ja detailDialogin inputFieldien tieto lähetetään viewModeliin. 

Viikkotehtävä 6:

Mitä room tekee: 
Rom on abstraktiokerros SQLiten päälle, joka tekee tietokannan käytöstä helppoa ja tyyppiturvallista. Roomin arkkitehtuuri koostuu kompnenteista entity, DAO ja database. Entity on kotlin luokka, joka edustaa tietokannan taulua. DAO sisältää metodit tietokannan käsittelyyn. Database on tietokantayhteys (singleton), joka tarjoaa pääsyn DAO:ihin. 

Projektin rakenne: 
Projektin rakenne koostuu viewistä, viewmodelista, repositorystä ja datasta. Data paketti sisältää Room komponentit. Repository paketti sisältää repository tiedoston, joka toimii välikätenä viewmodelin ja tietokannan kommunikoinnissa. Viewistä löytyy Ui tiedostot. 

Miten datavirta kulkee: 
esimerkkiksi: UI:ssa käyttäjä lisää taskin -> viewModel kutsuu repositorya -> Repository kutsuu DAO:ta -> database tallentaa SQLiteen 
