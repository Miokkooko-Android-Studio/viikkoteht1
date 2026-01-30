MVVM on suunnittelumalli, joka erottaa sovelluksen eri vastuualueet: Model, View, sekä Viewmodel. MVVM on hyödyllinen, sillä se kannustaa puhdasta koodia, sekä mahdollistaa helpomman testauksen ja ylläpidon.

Stateflow on tapa säilyttää ja jakaa UI:n tai logiikan tämänhetkinen tila muille komponenteille. StateFlow sopii jatkuviin tiloihin, jossa arvot voivat muuttua, esimerkiksi kun dataa latautuu tai käyttäjä on kirjautumassa sisään. 
private val _uiState = MutableStateFlow(UiState())
val uiState: StateFlow<UiState> = _uiState
Tämän näköisellä koodilla yleisesti käytetään stateflowta. Ylempi muuttuja on sisäisesti muokattava ja alempi on vain luettava versio joka jaetaan ulospäin. Näin seuraajat eivät pääse vahingossa muokkaamaan tilaa. 
val state by viewModel.uiState.collectAsState()
UI kuuntelee tämän näköisellä koodilla tilaa. Aina kun state muuttuu compose tekee uudelleenpiirron automaattisesti. 
