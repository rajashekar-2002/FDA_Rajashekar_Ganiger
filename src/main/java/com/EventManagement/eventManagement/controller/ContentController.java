package com.EventManagement.eventManagement.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;

import com.EventManagement.eventManagement.Repository.EventRepository;
import com.EventManagement.eventManagement.Repository.JoinedEventsRepository;
import com.EventManagement.eventManagement.Repository.MyUserRepository;
import com.EventManagement.eventManagement.Repository.UserRepositoryForService;
import com.EventManagement.eventManagement.model.Events;
import com.EventManagement.eventManagement.model.MyUser;
import com.EventManagement.eventManagement.updated_models.eventList_with_joined_Details;

@SuppressWarnings("all")
@Controller
public class ContentController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private final TemplateEngine templateEngine;


  @Autowired
  private MyUserRepository myUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepositoryForService userRepositoryForService;


  @Autowired
  private EventRepository eventRepository;


  @Autowired
  private JoinedEventsRepository joinedEventsRepository;

  public ContentController() {
    this.templateEngine = new TemplateEngine();
  }

 
  
  public ContentController(MyUserRepository myUserRepository, PasswordEncoder passwordEncoder, UserRepositoryForService userRepositoryForService, TemplateEngine templateEngine, JavaMailSender emailSender ) {
    this.mailSender=mailSender;
    this.templateEngine = templateEngine;
    this.myUserRepository = myUserRepository;
    this.passwordEncoder = passwordEncoder;

    this.userRepositoryForService = userRepositoryForService;
  }

  private String username;

  @SuppressWarnings("all")
  public Boolean check_user_Authenticated(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    username=auth.getName();
    if(username.equals("anonymousUser")){
      return false;
    }return true;
  }


  @GetMapping("/")
  public String handleWelcome(Model model) {
    if(!check_user_Authenticated()){ return "redirect:/login"; }
    List<Events> list_events = eventRepository.findAll();

    Long userId = userRepositoryForService.findByUsername(username).getId();
        
    List<eventList_with_joined_Details> eventsWithJoinedDetails = new ArrayList<>();

    for (Events event : list_events) {
      boolean joined = event.getIntegers().contains(userId);
      eventsWithJoinedDetails.add(new eventList_with_joined_Details(event, joined));
  }
    model.addAttribute("EventsList", eventsWithJoinedDetails);
    return "home";
  }






  @GetMapping("/user/event-add")
  public String addEventForm(Model model){
    if(!check_user_Authenticated()){ return "redirect:/login"; }
    Events event_obj = new Events();
    model.addAttribute("event", event_obj);
    return "EventForm";
  }

  @SuppressWarnings("all")
  @PostMapping("/user/event-add")
  public String addPropertyToDatabase(@ModelAttribute("event")Events event ,Model model){
    if(!check_user_Authenticated()){ return "redirect:/login"; }
    MyUser myUser=userRepositoryForService.findByUsername(username);
    event.setUsername(username);
    event.setEvent_name(event.getEvent_name());
    event.setDateField(event.getDateField());
    event.setDescription(event.getDescription());
    eventRepository.save(event);
    return "redirect:/user/myevents";
  }


  @SuppressWarnings("all")
  @GetMapping("/user/myevents")
  public String myevents(Model model){
    if(!check_user_Authenticated()){ return "redirect:/login"; }
    List<Events>list_events=new ArrayList<>();
    for(Events i : eventRepository.findAll()){
      if(i.getUsername().equals(username)){
        list_events.add(i);
      }
  }
  model.addAttribute("myevents", list_events);
  return "myevents";
}



@SuppressWarnings("all")
@GetMapping("/user/Joinedevents")
public String myJoinedevents(Model model){
  if(!check_user_Authenticated()){ return "redirect:/login"; }
 // Get the current user's ID
 Long userId = userRepositoryForService.findByUsername(username).getId();

 // Retrieve all events
 List<Events> allEvents = eventRepository.findAll();

 // Filter events to include only those where the userId is in the integers list
 List<Events> list_events = new ArrayList<>();
 for (Events event : allEvents) {
     if (event.getIntegers().contains(userId)) {
         list_events.add(event);
     }
 }
model.addAttribute("myevents", list_events);
return "myevents";
}








@SuppressWarnings("all")
@GetMapping("/user/event-join/{id}")
public String property_details(@PathVariable("id") Long id,Model model){
  if(!check_user_Authenticated()){ return "redirect:/login"; }
  Events event = eventRepository.findById(id).get();
  // MyUser event_owner=userRepositoryForService.findByUsername(eventRepository.findById(id).get().getUsername());
  // event.getIntegers().add(userRepositoryForService.findByUsername(username).getId());
      // Retrieve the current user's ID
      Long userId = userRepositoryForService.findByUsername(username).getId();
    
      // Check if the ID is already in the list
      if (event.getIntegers().contains(userId)) {
          // If present, remove the ID
          event.getIntegers().remove(userId);
      } else {
          // If not present, add the ID
          event.getIntegers().add(userId);
      }
  eventRepository.save(event);

  return "redirect:/";
}


  



  @PostMapping("/user/events/search")
  public String search(@RequestParam("search") String search,Model model){
    if(!check_user_Authenticated()){ return "redirect:/login"; }
    System.out.println("0000000000000");
    System.out.println(search);
    List<Events> events=eventRepository.findAll();
    List<Events> filterList = new ArrayList<>();
    for(Events i : events){
      
      if(i.getEvent_name().contains(search) || i.getDescription().contains(search)){
          filterList.add(i);
      }
    }
    System.out.println(filterList);



    Long userId = userRepositoryForService.findByUsername(username).getId();
        
    List<eventList_with_joined_Details> eventsWithJoinedDetails = new ArrayList<>();

    for (Events event : filterList) {
      boolean joined = event.getIntegers().contains(userId);
      eventsWithJoinedDetails.add(new eventList_with_joined_Details(event, joined));
  }



    model.addAttribute("EventsList", eventsWithJoinedDetails);
    return "home";

  }






  @GetMapping("/login")
  public String handleLogin(Model model) {
    return "custom_login";
  }

  @GetMapping("/logout")
  public String handleLogout() {
    return "custom_logout";
  }


    @GetMapping("/signup")
    public String signUp(Model model){
      model.addAttribute("user", new MyUser());
      return "signup";
    }



    @PostMapping("/signup")
    public String createUser(@ModelAttribute("user") MyUser user,Model model) {
      
        
        if(userRepositoryForService.findByUsernameOrId(user.getUsername(),user.getId()).isPresent()){
          model.addAttribute("user", new MyUser());
          model.addAttribute("error", "username already exist . ");
          return "signup";
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        myUserRepository.save(user);
        return "redirect:/login";
    }

}

