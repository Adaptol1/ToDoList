package main;

import main.model.Entry;
import main.model.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class DefaultController
{
    @Autowired
    private EntryRepository entryRepository;

    @RequestMapping("/")
    public String index(Model model)
    {
        Iterable<Entry> entryIterable = entryRepository.findAll();
        ArrayList<Entry> entries = new ArrayList<>();
        for(Entry entry : entryIterable)
        {
            entries.add(entry);
        }
//        model.addAttribute("entries", entries);
        model.addAttribute("entriesCount", entries.size());
        return "index";
    }
}
