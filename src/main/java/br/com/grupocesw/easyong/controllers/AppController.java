package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.mappers.UserMapper;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
import br.com.grupocesw.easyong.services.ConfirmationTokenService;
import br.com.grupocesw.easyong.services.RegistrationService;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.utils.PasswordVerificationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequiredArgsConstructor
@Slf4j
@ApiIgnore
public class AppController {

    private final RegistrationService registrationService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping(value = "/confirmation-account/{token}", produces = MediaType.TEXT_HTML_VALUE)
    public String confirmationAccount(@PathVariable String token, ModelMap model) {
        try {
            User user = registrationService.confirmUserAccount(token);
            model.addAttribute("message", String.format("%s sua conta foi ativada com sucesso. Seja bem-vindo!", user.getPerson().getName()));

            return "success-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "error-page";
    }

    @GetMapping(value = "/recover-password/{token}", produces = MediaType.TEXT_HTML_VALUE)
    public String recoverPassword(@PathVariable String token, ModelMap model) {
        try {
            model.addAttribute("userPasswordRequestDto", new UserPasswordRequestDto());
            model.addAttribute("token", token);
            confirmationTokenService.findByToken(token);

            return "change-password";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "error-page";
    }

    @PostMapping(value = "/recover-change-password/{token}")
    public String recoverChangePassword(
            UserPasswordRequestDto request,
            @PathVariable("token") String token,
            ModelMap model,
            RedirectAttributes redirectAttr
    ) {
        String redirectError = "redirect:/recover-password/" + token;

        try {
            redirectAttr.addFlashAttribute("token", token);

            PasswordVerificationUtil.isPasswordOkWithConfirmation(request.getPassword(), request.getPasswordConfirmation());
            User user = userService.confirmUserRecoverPassword(token, UserMapper.INSTANCE.requestDtoToEntity(request));

            model.addAttribute("message",
                    String.format("%s, sua senha foi alterada com sucesso!", user.getPerson().getName()));

            return "success-page";
        } catch (Exception e) {
            redirectAttr.addFlashAttribute("message", e.getMessage());
        }

        return redirectError;
    }

    @GetMapping(value = "/clean-cache-resources")
    @CacheEvict(value = {"ngos", "faqs", "socialCauses"}, allEntries = true)
    public String clearCacheResources(ModelMap model) {
        model.addAttribute("message", "Success. Clean Cache!");
        return "success-page";
    }
}
