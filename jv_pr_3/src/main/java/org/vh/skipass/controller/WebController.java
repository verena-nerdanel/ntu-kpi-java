package org.vh.skipass.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.vh.skipass.model.DayType;
import org.vh.skipass.model.TimeType;
import org.vh.skipass.service.TicketService;

import java.time.LocalDateTime;

@Controller
public class WebController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ModelAttribute("lang")
    public String getLanguage() {
        return localeResolver
                .resolveLocale(httpServletRequest)
                .getLanguage();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/new")
    public String newTicket() {
        return "new";
    }

    @PostMapping("/new")
    public String newTicketExecute(Model model,
                                   @RequestParam(name = "dayType") DayType dayType,
                                   @RequestParam(name = "timeType") TimeType timeType,
                                   @RequestParam(name = "maxDaysCount") int maxDaysCount,
                                   @RequestParam(name = "maxEntrancesCount") int maxEntrancesCount

    ) {
        final String id = ticketService.create(LocalDateTime.now(), dayType, timeType, maxDaysCount, maxEntrancesCount);
        model.addAttribute("id", id);
        model.addAttribute("data", ticketService.find(id));

        return "new";
    }

    @GetMapping("/manage")
    public String manageTickets(Model model) {
        model.addAttribute("tickets", ticketService.findAll());
        return "manage";
    }

    @PostMapping("/activate")
    public String activate(@RequestParam(name = "id") String id) {
        ticketService.activate(id);
        return "redirect:manage";
    }

    @PostMapping("/deactivate")
    public String deactivate(@RequestParam(name = "id") String id) {
        ticketService.deactivate(id);
        return "redirect:manage";
    }

    @GetMapping("/check")
    public String check(Model model, @RequestParam(name = "id", required = false) String id) {
        if (!StringUtils.isBlank(id)) {
            model.addAttribute("id", id);
            model.addAttribute("data", ticketService.find(id));
        }

        return "check";
    }

    @GetMapping("/pass")
    public String pass() {
        return "pass";
    }

    @PostMapping("/pass")
    public String passExecute(Model model, @RequestParam(name = "id", required = false) String id) {
        if (!StringUtils.isBlank(id)) {
            model.addAttribute("id", id);
            model.addAttribute("check", ticketService.pass(LocalDateTime.now(), id));
            model.addAttribute("data", ticketService.find(id));
        }

        return "pass";
    }
}
