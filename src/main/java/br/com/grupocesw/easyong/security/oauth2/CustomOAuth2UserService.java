package br.com.grupocesw.easyong.security.oauth2;

import br.com.grupocesw.easyong.entities.Person;
import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.AuthProvider;
import br.com.grupocesw.easyong.exceptions.OAuth2AuthenticationProcessingException;
import br.com.grupocesw.easyong.security.UserPrincipal;
import br.com.grupocesw.easyong.security.oauth2.user.OAuth2UserInfo;
import br.com.grupocesw.easyong.security.oauth2.user.OAuth2UserInfoFactory;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.utils.PasswordGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if(oAuth2UserInfo.getEmail().isEmpty())
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");

        Optional<User> userOptional = userService.findByUsername(oAuth2UserInfo.getEmail());
        User user;

        if(userOptional.isPresent()) {
            user = userOptional.get();

            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId())))
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");

            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
            .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
            .providerId(oAuth2UserInfo.getId())
            .username(oAuth2UserInfo.getEmail())
            .password(PasswordGeneratorUtil.generateStrongPassword())
            .picture(
                Picture.builder()
                    .url(oAuth2UserInfo.getImageUrl())
                .build()
            )
            .person(
                Person.builder()
                    .name(oAuth2UserInfo.getName())
                    .build()
            ).build();

        return userService.create(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.getPerson().setName(oAuth2UserInfo.getName());
        existingUser.getPicture().setUrl(oAuth2UserInfo.getImageUrl());

        return userService.update(existingUser.getId(), existingUser);
    }

}