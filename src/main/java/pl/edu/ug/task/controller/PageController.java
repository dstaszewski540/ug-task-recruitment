package pl.edu.ug.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for pages
 *
 * @author Damian Staszewski
 */
@Controller
@RequiredArgsConstructor
public class PageController {

    /**
     * Index page
     *
     * @return index page name
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

//    /**
//     * Error page
//     * @return error page named index
//     */
//    @GetMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
//    public String error(HttpServletRequest request, Model model) {
//        StreamSupport.stream(Spliterators.spliteratorUnknownSize(request.getAttributeNames().asIterator(), Spliterator.SIZED), false)
//                        .forEach(it -> System.out.println(it + ": " + request.getAttribute(it)));
//        model.addAttribute("error", Map.of(
//                "message", request.getAttribute("jakarta.servlet.error.message"),
//                "code", request.getAttribute("jakarta.servlet.error.status_code"),
//                "name", HttpStatus.valueOf(request.getAttribute("jakarta.servlet.error.status_code").toString()).getReasonPhrase()
//        ));
//        return "index";
//    }
}
