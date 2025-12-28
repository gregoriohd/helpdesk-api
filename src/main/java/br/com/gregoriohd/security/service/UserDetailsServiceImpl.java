package br.com.gregoriohd.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gregoriohd.domain.Pessoa;
import br.com.gregoriohd.repsository.PessoaRepository;
import br.com.gregoriohd.security.UserSpringSecurity;
//implementacao responsável por carregar usuários
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private PessoaRepository repository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
//		Optional<Pessoa> user = repository.findByEmail(email);
//		if(user.isPresent()) {
//			final Pessoa p = user.get();
//			return new UserSpringSecurity(p.getId(), p.getEmail(), p.getSenha(), p.getPerfis());
//		}
//		throw new UsernameNotFoundException(email); 
		
		final Pessoa p = repository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException(email));
		//retorna a representacao do usuario autenticado
		return new UserSpringSecurity(p.getId(), p.getEmail(), p.getSenha(), p.getPerfis());
		
	}

}
