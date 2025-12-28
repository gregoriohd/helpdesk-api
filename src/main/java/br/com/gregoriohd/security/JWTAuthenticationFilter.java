package br.com.gregoriohd.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gregoriohd.security.dto.CredenciaisDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//filtro de autenticacao
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/login");
	}
	//processar as credenciais enviadas via formulário de login
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			
			CredenciaisDTO creDto = new ObjectMapper()
					.readValue(request.getInputStream(), CredenciaisDTO.class);
			
			//Cria token de autenticação
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(
							creDto.email(), creDto.senha(), new ArrayList());
			
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}
	//chamado após uma autenticação bem-sucedida. 
	//responsável por configurar a resposta após o login do usuário
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String userName = ((UserSpringSecurity) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(userName);
		
		response.setHeader("access-control-expose-headers", "Authorization");
		response.setHeader("Authorization", "Bearer " + token);
		//System.out.println(token);
		//super.successfulAuthentication(request, response, chain, authResult);
		  // Configurar cookie seguro
//	    Cookie authCookie = new Cookie("AUTH_TOKEN", token);
//	    authCookie.setHttpOnly(true); // Protege contra XSS
//	    authCookie.setSecure(true); // Apenas HTTPS
//	    authCookie.setPath("/");
//	    authCookie.setMaxAge(7 * 24 * 60 * 60); // 7 dias
//	    authCookie.setAttribute("SameSite", "Strict");
//	    
//	    response.addCookie(authCookie);
	    
	    // Redirecionar
	    //response.sendRedirect("/dashboard");
	}
	//camado quando credenciais nao autorizadas
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(jsonMessageUnauthorized());
	}
	
	private CharSequence jsonMessageUnauthorized() {
		long date = new Date().getTime();
		return "{" 
					+"\"timestamp\": "  + date + ", "
					+"\"status\": 401, "
					+"\"error\": \"Nao Autorizado\", "
					+"\"message\": \"Email ou senha invalidos\", "
					+"\"path\": \"/login\"}";
	}

}
