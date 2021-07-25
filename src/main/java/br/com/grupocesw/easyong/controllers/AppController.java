package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.mappers.UserMapper;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
import br.com.grupocesw.easyong.services.ConfirmationTokenService;
import br.com.grupocesw.easyong.services.RegistrationService;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.utils.MavenPomPropertyUtil;
import br.com.grupocesw.easyong.utils.PasswordVerificationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class AppController {

    private static final Model pomModel = MavenPomPropertyUtil.getPom();
    private final RegistrationService registrationService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping
    public String index(ModelMap model) {
        model.addAttribute("message", "Bem vindo ao " + pomModel.getName());

        return "index";
    }

    @GetMapping("/terms")
    public String terms() {
        return "pages/terms";
    }

    @GetMapping("/terms-without-header")
    public String termsV2(ModelMap model) {
        model.addAttribute("hideHeader", true);
        return "pages/terms";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return "pages/privacy-policy";
    }

    @GetMapping("/privacy-policy-without-header")
    public String privacyPolicyV2(ModelMap model) {
        model.addAttribute("hideHeader", true);

        return "pages/privacy-policy";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/about";
    }

    @GetMapping("/about-without-header")
    public String aboutV2(ModelMap model) {
        model.addAttribute("hideHeader", true);

        return "pages/about";
    }

    @GetMapping(value = "/confirmation-account/{token}", produces = MediaType.TEXT_HTML_VALUE)
    public String confirmationAccount(@PathVariable String token, ModelMap model) {
        try {
            User user = registrationService.confirmUserAccount(token);
            model.addAttribute("message", String.format("%s sua conta foi ativada com sucesso!", user.getPerson().getName()));
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }

        return "index";
    }

    @GetMapping(value = "/recover-password/{token}", produces = MediaType.TEXT_HTML_VALUE)
    public String recoverPassword(@PathVariable String token, ModelMap model) {
        try {
            model.addAttribute("userPasswordRequestDto", new UserPasswordRequestDto());
            model.addAttribute("token", token);
            confirmationTokenService.findByToken(token);

            return "pages/change-password";
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }

        return "index";
    }

    @PostMapping(value = "/recover-change-password/{token}")
    public String recoverChangePassword(
            UserPasswordRequestDto request,
            @PathVariable("token") String token,
            ModelMap model,
            RedirectAttributes redirectAttr
    ) {
        try {
            redirectAttr.addFlashAttribute("token", token);

            PasswordVerificationUtil.isPasswordOkWithConfirmation(request.getPassword(), request.getPasswordConfirmation());
            User user = userService.confirmUserRecoverPassword(token, UserMapper.INSTANCE.requestDtoToEntity(request));

            model.addAttribute("message",
                    String.format("%s sua senha foi alterada com sucesso!", user.getPerson().getName()));

            return "index";
        } catch (Exception e) {
            redirectAttr.addFlashAttribute("color", "danger");
            redirectAttr.addFlashAttribute("alertMessage", e.getMessage());
        }

        return "redirect:/recover-password/" + token;
    }

    @GetMapping(value = "/clean-cache-resources")
    @CacheEvict(value = {"ngos", "faqs", "socialCauses", "cities"}, allEntries = true)
    public String clearCacheResources(ModelMap model) {
        model.addAttribute("message", "Success. Clean Cache!");

        return "index";
    }
}
