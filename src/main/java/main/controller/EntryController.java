package main.controller;


import main.model.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import main.model.Entry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class EntryController
{
    @Autowired
    private EntryRepository entryRepository;

    @GetMapping("/entries/")
    public List<Entry> list()
    {
        Iterable<Entry> entryIterable = entryRepository.findAll();
        ArrayList<Entry> entries = new ArrayList<>();
        for(Entry entry : entryIterable) {
            entries.add(entry);
        }
        return entries;
//        return Storage.getAllEntries();
    }
    @PostMapping("/entries/")
    public int add(Entry entry)
    {
        Entry newEntry = entryRepository.save(entry);
        return newEntry.getId();
    }
    @GetMapping("/entries/{id}")
    public ResponseEntity get(@PathVariable int id)
    {
        Optional<Entry> optionalEntry = entryRepository.findById(id);
//        Entry entry = Storage.getEntry(id);
        if(!optionalEntry.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalEntry.get(), HttpStatus.OK);
    }
    @DeleteMapping("/entries/{id}")
    public ResponseEntity delete(@PathVariable int id)
    {
        Optional <Entry> optionalEntry = entryRepository.findById(id);
//        Entry entry = Storage.getEntry(id);
        if(optionalEntry.get() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        Storage.deleteEntry(id);
        entryRepository.delete(optionalEntry.get());
        return new ResponseEntity(optionalEntry.get(), HttpStatus.OK);
    }
    @DeleteMapping("/entries/")
    public ResponseEntity deleteAll ()
    {
        if(entryRepository.count() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        Storage.deleteAllEntries();
        entryRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("/entries/{id}")
    public ResponseEntity update (@PathVariable int id,
                                  @RequestParam String toDo,
                                  @RequestParam String executor)
    {
        Optional <Entry> optionalEntry = entryRepository.findById(id);
        optionalEntry.get().setToDo(toDo);
        optionalEntry.get().setExecutor(executor);
        entryRepository.save(optionalEntry.get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
