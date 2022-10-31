package tb.soft;

import java.io.*;
import java.util.*;

/*
 * Program: Aplikacja działająca w oknie konsoli, która umożliwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonConsoleApp.java
 *          
 *   Autor: Paweł Rogaliński
 *    Data: październik 2018 r.
 */
public class PersonConsoleApp {

	private static final String GREETING_MESSAGE = 
			"Program Person - wersja konsolowa\n" + 
	        "Autor: Paweł Rogaliński\n" +
			"Data:  październik 2018 r.\n";

	private static final String COLLECTION_MENU =
			" Wybierz typ kolekcji:		\n" +
			"1 - HashSet				\n" +
			"2 - TreeSet				\n" +
			"3 - LinkedList				\n" +
			"4 - ArrayList				\n" +
			"5 - HashMap				\n" +
			"6 - TreeMap				\n" +
			"0 - Zakończ program		\n";
	private static final String MENU = 
			"    M E N U   G Ł Ó W N E  \n" +
			"1 - Podaj dane nowej osoby \n" +
			"2 - Usuń dane osoby        \n" +
			"3 - Modyfikuj dane osoby   \n" +
			"4 - Wczytaj dane z pliku   \n" +
			"5 - Zapisz dane do pliku   \n" +
			"6 - equals() porównanie	\n" +
			"7 - hashCode() porównanie	\n" +
			"0 - Zakończ program        \n";	
	
	private static final String CHANGE_MENU = 
			"   Co zmienić?     \n" + 
	        "1 - Imię           \n" + 
			"2 - Nazwisko       \n" + 
	        "3 - Rok urodzenia  \n" + 
			"4 - Stanowisko     \n" +
	        "0 - Powrót do menu głównego\n";

	
	/**
	 * ConsoleUserDialog to pomocnicza klasa zawierająca zestaw
	 * prostych metod do realizacji dialogu z użytkownikiem
	 * w oknie konsoli tekstowej.
	 */
	// private static final ConsoleUserDialog UI = new JOptionUserDialog();
	private static final ConsoleUserDialog UI = new ConsoleUserDialog();


	public static void main(String[] args) {
		// Utworzenie obiektu aplikacji konsolowej
		// oraz uruchomienie głównej pętli aplikacji.
		PersonConsoleApp application = new PersonConsoleApp();
		application.runMainLoop();
	} 

	
	/*
	 *  Referencja do obiektu, który zawiera dane aktualnej osoby.
	 */
	private Person currentPerson = null;
	private boolean choice = false;
	private AbstractCollection<Person> setList = null;
	private AbstractCollection<Person_defined> setListD = null;
	private AbstractMap<Integer, Person> map = null;

	/*
	 *  Metoda runMainLoop wykonuje główną pętlę aplikacji.
	 *  UWAGA: Ta metoda zawiera nieskończoną pętlę,
	 *         w której program się zatrzymuje aż do zakończenia
	 *         działania za pomocą metody System.exit(0); 
	 */
	public void runMainLoop() {
		UI.printMessage(GREETING_MESSAGE);

		while(!choice)
		{
			switch(UI.enterInt(COLLECTION_MENU + "==>> ")) {
				case 1:	//ini hashSet
				{
					setList = new HashSet<Person>();
					setListD = new HashSet<Person_defined>();
					choice = true;
				}
				break;
				case 2: //ini treeSet
				{
					setList = new TreeSet<Person>();
					setListD = new TreeSet<Person_defined>();
					choice = true;
				}
				break;
				case 3: //ini linkedList
				{
					choice = true;
					setList = new LinkedList<Person>();
					setListD = new LinkedList<Person_defined>();
				}
				break;
				case 4: //ini arrayList
				{
					choice = true;
					setList = new ArrayList<Person>();
					setListD = new ArrayList<Person_defined>();
				}
				break;
				case 5:
					break;
				case 6:
					break;
				case 0:{
					// zakończenie działania programu
					UI.printInfoMessage("\nProgram zakończył działanie!");
					choice = true;
					System.exit(0);
				}
				default:
				{
					choice = false;
					UI.printErrorMessage("Wybierz sposób zapisu danych!");
				}
			}
		}

		while (true) {
			UI.clearConsole();
			Iterator <Person> iter = setList.iterator();
			Iterator <Person_defined> iterD = setListD.iterator();
			// showCurrentPerson();
			while(iter.hasNext())
			{
				showPerson(iter.next());
			}
			UI.printMessage("---------------");
			while(iterD.hasNext())
			{
				showPerson(iterD.next());
			}

			try {
				switch (UI.enterInt(MENU + "==>> ")) {
				case 1:
					// utworzenie nowej osoby
					Person personTemp = createNewPerson();
					Person_defined personDTemp = new Person_defined(personTemp);
					setList.add(personTemp);
					setListD.add(personDTemp);
					break;
				case 2:
					// usunięcie danych aktualnej osoby.
					Integer toRemoveInt = UI.enterInt("Obiekt do usunięcia ==>> ");
					iter = setList.iterator();
					iterD = setListD.iterator();
					while(toRemoveInt >= 0 && iter.hasNext())
					{
						Person toRemove = iter.next();
						Person_defined toRemoveD = iterD.next();
						// https://stackoverflow.com/questions/43690009/how-to-remove-an-object-from-a-linked-list-in-java
						if(toRemoveInt == 0)
						{
							iter.remove();
							iterD.remove();
						}
						toRemoveInt--;
					}
					UI.printInfoMessage("Dane aktualnej osoby zostały usunięte");
					break;
				case 3:
					// zmiana danych dla aktualnej osoby
					if (setList.isEmpty() == true || setListD.isEmpty() == true) throw new PersonException("Żadna osoba nie została utworzona.");
					iter = setList.iterator();
					iterD = setListD.iterator();
					Person toEdit = null;
					Person_defined toEditD = null;
					while(iter.hasNext())
					{
						toEdit = iter.next();
						if(UI.enterInt("Obiekt do edycji z kolekcji bazowej?: " + toEdit + "\n" +
								"0 - nie\n" +
								"1 - tak\n ==>> ") == 1)
						{
							setList.remove(toEdit);
							break;
						}
					}
					while(iterD.hasNext())
					{
						toEditD = iterD.next();
						if(UI.enterInt("Obiekt do edycji z kolekcji zdefiniowanej?: " + toEditD + "\n" +
								"0 - nie\n" +
								"1 - tak\n ==>> ") == 1)
						{
							setListD.remove(toEditD);
							break;
						}
					}

					changePersonData(toEdit);
					setList.add(toEdit);
					changePersonData(toEditD);
					setListD.add(toEditD);
					break;
				case 4: {
					// odczyt danych z pliku tekstowego.
					String file_name = UI.enterString("Podaj nazwę pliku: ");
					loadAllFromFile(file_name, setList, setListD);
					UI.printInfoMessage("Dane aktualnej osoby zostały wczytane z pliku " + file_name);
				}
					break;
				case 5: {
					// zapis danych aktualnej osoby do pliku tekstowego 
					String file_name = UI.enterString("Podaj nazwę pliku: ");
					Person.printTableToFile(file_name, setList);
					Person_defined.printTableToFileDefined(file_name, setListD);
					UI.printInfoMessage("Dane aktualnej osoby zostały zapisane do pliku " + file_name);
				}
					break;
				case 6:
					// porównanie obiektów z pomocą zdefiniowanej funkcji equals() oraz przy jej braku
					equalsCompare(setList, setListD);
					break;
				case 7:
					// porównanie obiektów z pomocą zdefiniowanej funkcji hashCode() oraz przy jej braku
					hashCodeCompare(setList, setListD);
					break;
				case 0:
					// zakończenie działania programu
					UI.printInfoMessage("\nProgram zakończył działanie!");
					System.exit(0);
				} // koniec instrukcji switch
			} catch (PersonException e) { 
				// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
				// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		} // koniec pętli while
	}
	
	
	/*
	 *  Metoda wyświetla w oknie konsoli dane aktualnej osoby 
	 *  pamiętanej w zmiennej currentPerson.
	 */
	void showCurrentPerson() {
		showPerson(currentPerson);
	} 

	static void loadAllFromFile(String file_name, AbstractCollection<Person> list, AbstractCollection<Person_defined> listD) throws PersonException {
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)));

			String line = null;
			line = reader.readLine();
			while(line != null)
			{
				String[] txt = line.split("#");
				Person person = new Person(txt[0], txt[1]);
				Person_defined personD = new Person_defined(txt[0], txt[1]);
				person.setBirthYear(txt[2]);
				personD.setBirthYear(txt[2]);
				person.setJob(txt[3]);
				personD.setJob(txt[3]);
				list.add(person);
				listD.add(personD);
				line = reader.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			throw new PersonException("Nie odnaleziono pliku " + file_name);
		}
		catch(IOException e){
			throw new PersonException("Wystąpił błąd podczas odczytu danych z pliku.");
		}
	}

	/*
	* Porównanie obiektów przy użyciu equals()
	*/
	static void equalsCompare(AbstractCollection<Person> list, AbstractCollection<Person_defined> listD)
	{
		if(list.size() > 1)
		{
			Iterator <Person> iter = list.iterator();
			Person person = iter.next();
			Person personCompare = null;
			while(iter.hasNext())
			{
				personCompare = iter.next();
				if(person.equals(personCompare))
				{
					UI.printMessage("----------------------");
					UI.printMessage(person + " jest tym samym obiektem co " + personCompare + " według equals() w kolekcji podstawowej.");
				}
				else
				{
					UI.printMessage("----------------------");
					UI.printMessage(person + " NIE jest tym samym obiektem co " + personCompare + " według equals() w kolekcji podstawowej.");
				}
			}
		}
		else
		{
			UI.printErrorMessage("Za mała ilość obiektów w kolekcji podstawowej aby porównać!");
		}

		if(listD.size() > 1)
		{
			Iterator <Person_defined> iterD = listD.iterator();
			Person_defined personD = iterD.next();
			Person_defined personCompareD = null;
			while(iterD.hasNext())
			{
				personCompareD = iterD.next();
				if(personD.equals(personCompareD))
				{
					UI.printMessage("----------------------");
					UI.printMessage(personD + " jest tym samym obiektem co " + personCompareD + " według equals() w kolekcji zdefiniowanej.");
				}
				else
				{
					UI.printMessage("----------------------");
					UI.printMessage(personD + " NIE jest tym samym obiektem co " + personCompareD + " według equals() w kolekcji zdefiniowanej.");
				}
			}
		}
		else
		{
			UI.printErrorMessage("Za mała ilość obiektów w kolekcji zdefiniowanej aby porównać!");
		}
	}

	/*
	 * Porównanie obiektów przy użyciu hashCode()
	 */
	static void hashCodeCompare(AbstractCollection<Person> list, AbstractCollection<Person_defined> listD)
	{
		if(list.size() > 1)
		{
			Iterator <Person> iter = list.iterator();
			Person person = iter.next();
			Person personCompare = null;
			while(iter.hasNext())
			{
				personCompare = iter.next();
				if(person.hashCode() == personCompare.hashCode())
				{
					UI.printMessage("----------------------");
					UI.printMessage(person + " jest tym samym obiektem co " + personCompare + " według hashCode() w kolekcji podstawowej.");
				}
				else
				{
					UI.printMessage("----------------------");
					UI.printMessage(person + " NIE jest tym samym obiektem co " + personCompare + " według hashCode() w kolekcji podstawowej.");
				}
			}
		}
		else
		{
			UI.printErrorMessage("Za mała ilość obiektów w kolekcji podstawowej aby porównać!");
		}

		if(listD.size() > 1)
		{
			Iterator <Person_defined> iterD = listD.iterator();
			Person_defined personD = iterD.next();
			Person_defined personCompareD = null;
			while(iterD.hasNext())
			{
				personCompareD = iterD.next();
				if(personD.hashCode() == personCompareD.hashCode())
				{
					UI.printMessage("----------------------");
					UI.printMessage(personD + " jest tym samym obiektem co " + personCompareD + " według hashCode() w kolekcji zdefiniowanej.");
				}
				else
				{
					UI.printMessage("----------------------");
					UI.printMessage(personD + " NIE jest tym samym obiektem co " + personCompareD + " według hashCode() w kolekcji zdefiniowanej.");
				}
			}
		}
		else
		{
			UI.printErrorMessage("Za mała ilość obiektów w kolekcji zdefiniowanej aby porównać!");
		}
	}

	/* 
	 * Metoda wyświetla w oknie konsoli dane osoby reprezentowanej 
	 * przez obiekt klasy Person
	 */ 
	static void showPerson(Person person) {
		StringBuilder sb = new StringBuilder();
		
		if (person != null) {
			sb.append("Aktualna osoba: \n")
			  .append("      Imię: ").append(person.getFirstName()).append("\n")
			  .append("  Nazwisko: ").append(person.getLastName()).append("\n")
			  .append("   Rok ur.: ").append(person.getBirthYear()).append("\n")
			  .append("Stanowisko: ").append(person.getJob()).append("\n");
		} else
			sb.append( "Brak danych osoby\n" );
		UI.printMessage( sb.toString() );
	}

	
	/* 
	 * Metoda wczytuje w konsoli dane nowej osoby, tworzy nowy obiekt
	 * klasy Person i wypełnia atrybuty wczytanymi danymi.
	 * Walidacja poprawności danych odbywa się w konstruktorze i setterach
	 * klasy Person. Jeśli zostaną wykryte niepoprawne dane,
	 * to zostanie zgłoszony wyjątek, który zawiera komunikat o błędzie.
	 */
	static Person createNewPerson(){
		String first_name = UI.enterString("Podaj imię: ");
		String last_name = UI.enterString("Podaj nazwisko: ");
		String birth_year = UI.enterString("Podaj rok ur.: ");
		UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
		String job_name = UI.enterString("Podaj stanowisko: ");
		Person person;
		try { 
			// Utworzenie nowego obiektu klasy Person oraz
			// ustawienie wartości wszystkich atrybutów.
			person = new Person(first_name, last_name);
			person.setBirthYear(birth_year);
			person.setJob(job_name);
		} catch (PersonException e) {    
			// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
			// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
			// poszczególnych atrybutów.
			// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return person;
	}
	
	
	/* 
	 * Metoda pozwala wczytać nowe dane dla poszczególnych atrybutów 
	 * obiekty person i zmienia je poprzez wywołanie odpowiednich setterów z klasy Person.
	 * Walidacja poprawności wczytanych danych odbywa się w setterach
	 * klasy Person. Jeśli zostaną wykryte niepoprawne dane,
	 * to zostanie zgłoszony wyjątek, który zawiera komunikat o błędzie.
	 */
	static void changePersonData(Person person)
	{
		while (true) {
			UI.clearConsole();
			showPerson(person);

			try {		
				switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
				case 1:
					person.setFirstName(UI.enterString("Podaj imię: "));
					break;
				case 2:
					person.setLastName(UI.enterString("Podaj nazwisko: "));
					break;
				case 3:
					person.setBirthYear(UI.enterString("Podaj rok ur.: "));
					break;
				case 4:
					UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
					person.setJob(UI.enterString("Podaj stanowisko: "));
					break;
				case 0: return;
				}  // koniec instrukcji switch
			} catch (PersonException e) {     
				// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
				// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	
}  // koniec klasy PersonConsoleApp
