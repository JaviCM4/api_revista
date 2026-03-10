package revista_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import revista_backend.models.credential.Credential;
import revista_backend.repositories.credential.CredentialRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    @Autowired
    public JwtUserDetailsService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential cred = credentialRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        Collection<GrantedAuthority> authorities;
        if (cred.getUser() != null && cred.getUser().getUserType() != null) {
            String roleName = cred.getUser().getUserType().getName();
            // normalizamos mayúsculas por convención
            roleName = roleName.toUpperCase();
            // otorgamos la autoridad usando el nombre del rol sin prefijo
            authorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));
        } else {
            authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        }

        return new org.springframework.security.core.userdetails.User(cred.getUsername(), cred.getPassword(), authorities);
    }
}
