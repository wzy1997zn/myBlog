package com.zywang.myblog.controller.admin;

import com.zywang.myblog.po.Tag;
import com.zywang.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String listTags(@PageableDefault(size = 2) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTags(pageable));
        return "admin/manage_tag";
    }

    @GetMapping("/tag/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/edit_tag";
    }

    @GetMapping("/tag/{id}/input")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/edit_tag";
    }

    @PostMapping("/tag")
    public String post(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());// use name to check whether there is same one
        if (tag1 != null) {
            bindingResult.rejectValue("name", "nameError", tag.getName() + " already exists.");
        }
        if (bindingResult.hasErrors()) {
            return "admin/edit_tag";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "Option failed.");
        } else {
            attributes.addFlashAttribute("message", "Succeed!");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tag/{id}")
    public String post(@Valid Tag tag, BindingResult bindingResult,@PathVariable Long id,  RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());// use name to check whether there is same one
        if (tag1 != null) {
            bindingResult.rejectValue("name", "nameError", tag.getName() + " already exists.");
        }
        if (bindingResult.hasErrors()) {
            return "admin/edit_tag";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "Option failed.");
        } else {
            attributes.addFlashAttribute("message", "Succeed!");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tag/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "Succeed!");
        return "redirect:/admin/tags";
    }
}
