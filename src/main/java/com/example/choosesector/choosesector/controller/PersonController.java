package com.example.choosesector.choosesector.controller;

import com.example.choosesector.choosesector.entity.PersonEntity;
import com.example.choosesector.choosesector.entity.SectorEntity;
import com.example.choosesector.choosesector.repository.PersonRepository;
import com.example.choosesector.choosesector.repository.SectorRepository;
import org.apache.logging.log4j.util.PropertySource;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PersonController {
    @Autowired
    private SectorRepository sectorRepository;



    @Autowired
    private PersonRepository personRepository;
    @PostMapping("choose_sector")
    public String choose_sector(
            @RequestParam("sector") Long[] choosed){
        Iterable<SectorEntity> sectorEntities= sectorRepository.findAllById(Arrays.asList(choosed));
        Set<SectorEntity> origanalSectorList = new HashSet<>();
        sectorEntities.forEach(origanalSectorList::add);


        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        PersonEntity personEntity = personRepository.findByUsername(username);
        personEntity.setChosedSectors(origanalSectorList);

        personRepository.save(personEntity);
        return "redirect:/index-2";
    }

    @GetMapping("/index-2")
    public String chooseSectorShow(
            Model model){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Iterable<SectorEntity> sectorEntities = sectorRepository.findAll();
        List<SectorEntity> sectorEntityList = new ArrayList<>();
        sectorEntities.forEach(sectorEntityList::add);
        Comparator<SectorEntity> sectorGroupComparator = Comparator.comparing(SectorEntity::getGroup);
        Collections.sort(sectorEntityList, sectorGroupComparator);

        List<String> choosedNameSec = personRepository.findByUsername(username).getChosedSectors()
                .stream().map(it-> it.getSector()).collect(Collectors.toList());

        model.addAttribute("sectors", sectorEntityList);
        model.addAttribute("strSector", choosedNameSec);
        model.addAttribute("username", username);

        return "index-2";
    }

}
