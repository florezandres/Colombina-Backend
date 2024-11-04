package com.example.colombina.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.colombina.services.JwtService;
import com.example.colombina.services.UsuarioService;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Obtener el token JWT desde las cabeceras
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            System.out.println("Auth Header: " + authHeader); // Log para verificar el token

            if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                String jwt = authHeader.substring(7); // Remueve el prefijo "Bearer "
                String userName = jwtService.extractUserName(jwt);

                if (StringUtils.hasText(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userName);
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        // Establecer la autenticación en el contexto de seguridad
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        accessor.setUser(authToken); // Establecer el usuario en el contexto de WebSocket
                        return message;
                    }
                }
            }

            // Si el token no es válido, lanza una excepción o rechaza la conexión
            throw new IllegalArgumentException("Token no válido");
        }

        return message;
    }
}
