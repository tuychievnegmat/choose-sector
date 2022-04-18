package com.example.choosesector.choosesector.controller;

import com.example.choosesector.choosesector.entity.SectorEntity;
import com.example.choosesector.choosesector.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Iterator;
import java.util.Optional;


@Controller
public class SectorController {
    @Autowired
    SectorRepository sectorRepository;

    //in the future need refactor maybe with hiberanate validator
    //with another way
    @PostMapping("add")
    public String addSector(
            @RequestParam String sector,
            @RequestParam String group,
            @ModelAttribute("add_sector") final Object addSector,
            RedirectAttributes model) {

        if(sectorRepository.findBySector(sector) == null
                && !sector.isBlank()
                && !group.isBlank()){
            SectorEntity sectorEntity = new SectorEntity(sector, group, null);
            sectorRepository.save(sectorEntity);
            model.addAttribute("add_sector", "success add new sector");
        }
        else {
            model.addAttribute("add_sector", "sector already exists or sector or/end group empty");
        }

        return "redirect:/sectors";
    }

    @PostMapping("remove")
    public String removeSector(@RequestParam Long remove, Model model) {
        Iterable<SectorEntity> sectorEntities = sectorRepository.findAll();
        model.addAttribute("sectors", sectorEntities);
        Optional<SectorEntity> sectorEntity = sectorRepository.findById(remove);

        if (!sectorEntity.isEmpty() &&
                remove != null &&
                sectorEntity != null &&
                remove.equals(sectorEntity.get().getId())) {

            if(sectorRepository.findById(remove) != null)
                 sectorRepository.delete(sectorEntity.get());
        }
        return "redirect:/sectors";

    }

    @GetMapping("/sectors")
    public String allSectors(
            @ModelAttribute("add_sector") final Object addSector,
            Model model) {

        Iterable<SectorEntity> sectorEntities = sectorRepository.findAll();
        String addSectorStr = "";
        if(!addSector.toString().contains("java.lang.Object"))
            addSectorStr = addSector.toString();

        model.addAttribute("add_sector", addSectorStr);
        model.addAttribute("sectors", sectorEntities);
        return "sectors";
    }

    @PostMapping("filter")
    public String filterGroup(
            @RequestParam String filter,
            Model model
    ){
        Iterable<SectorEntity> sectorEntities;
        if(filter != null && !filter.isEmpty()) {
            sectorEntities = sectorRepository.findByGroup(filter);
        }else {
            sectorEntities = sectorRepository.findAll();
        }
        model.addAttribute("sectors", sectorEntities);
        return "sectors";
    }



}
