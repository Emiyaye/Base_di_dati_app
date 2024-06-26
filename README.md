L’applicazione gestisce due tipologie di utenti: amministratori e utenti; 
perciò la schermata iniziale richiede di specificare se si è utenti o amministratori. 
Nel caso di accesso come amministratore, è necessario inserire un'email e una password, 
mentre per gli utenti non è richiesta questa procedura.

Nell'interfaccia utente avrà diverse operazioni di inserimento e selezione. 
Se i dati inseriti sono mancanti o errati, verrà visualizzato un messaggio di errore
tramite un JOptionPane, che specificherà la causa del fallimento dell'operazione.
Anche se l'account utente non necessita di un login, non sarà possibile inserire l'email di un amministratore nelle operazione per gli utenti;
per alcuni operazioni sarà necessario inserire il proprio account. Per le operazioni di selezione, verrà mostrata una JTextField
e una o più tabelle. In base al contenuto del campo di testo, cliccando il bottone di ricerca
le tabelle verrano aggiornate in base ai dati di input.

Nell'interfaccia amministratore, prima di poter accedere alle operazioni riservate agli amministratori è necessario
inserire email e password valide. In caso contrario non sarà permesso l'accesso all'interfaccia
admin, le operazioni disponibili per gli amminisratori includono operazioni di inserimento, selezione e aggiornamento.
In tutte le interfaccie sarà presente un bottone back che permetterà di tornare al pannello di menu.
