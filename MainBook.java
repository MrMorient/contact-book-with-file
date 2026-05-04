package app.ContactBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class ContactBook {
    ArrayList<Contact> contacts = new ArrayList<>();

    // Adds contacts to the list
    void addContact(String name, String phone, String email) {
        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added!");
    }

    // Sorts contacts by name
    void sortContacts() {
        Collections.sort(contacts, new Comparator<Contact>() {
            public int compare(Contact c1, Contact c2) {
                return c1.name.compareToIgnoreCase(c2.name);
            }
        });
    }

    // Views the contacts in the list
    void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        sortContacts(); // always sorted

        for (Contact c : contacts) {
            c.display();
        }
    }

    // Binary search for contact by name
    Contact binarySearch(String name) {
        sortContacts();

        int left = 0;
        int right = contacts.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            int result = contacts.get(mid).name.compareToIgnoreCase(name);

            if (result == 0) {
                return contacts.get(mid);
            } else if (result < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    // Searches using the binary search method
    void searchContact(String name) {
        Contact c = binarySearch(name);

        if (c != null) {
            c.display();
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Deletes the contact
    void deleteContact(String name) {
        Contact c = binarySearch(name);

        if (c != null) {
            contacts.remove(c);
            System.out.println("Contact deleted!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Edits the contact
    void editContact(String name, String newPhone, String newEmail) {
        Contact c = binarySearch(name);

        if (c != null) {
            c.phone = newPhone;
            c.email = newEmail;
            System.out.println("Contact updated!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Saves to a file
    void saveToFile() {
        try {
            FileWriter writer = new FileWriter("contacts.txt");

            for (Contact c : contacts) {
                writer.write(c.name + "," + c.phone + "," + c.email + "\n");
            }

            writer.close();
            System.out.println("Contacts saved!");

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    // Loads from a file
    void loadFromFile() {
        try {
            File file = new File("contacts.txt");

            if (!file.exists()) {
                return;
            }

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}
