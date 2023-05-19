package main;

import main.model.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage
{
    private static int currentId = 1;
    public static HashMap<Integer, Entry> entries = new HashMap<Integer, Entry>();

    public static List<Entry> getAllEntries ()
    {
        ArrayList<Entry> entriesList = new ArrayList<Entry>();
        entriesList.addAll(entries.values());
        return entriesList;
    }

    public static int addEntry(Entry entry)
    {
        int id = currentId++;
        entry.setId(id);
        entries.put(id, entry);
        return id;
    }

    public static Entry getEntry(int entryId)
    {
        if(entries.containsKey(entryId)) {
            return entries.get(entryId);
        }
        return null;
    }

    public static void deleteEntry (int entryId)
    {
        entries.remove(entryId);
        if(currentId > 1)
        {
            currentId--;
        }
    }

    public static void deleteAllEntries ()
    {
        int i = 1;
        while(!entries.isEmpty())
        {
            Storage.deleteEntry(i);
            i++;
        }
        currentId = 1;
    }

}
