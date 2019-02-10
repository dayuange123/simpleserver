package club.dayuange.mypacket.filter;

import club.dayuange.mypacket.request.SimpleRequest;
import club.dayuange.mypacket.response.SimpleResponse;

public interface FilterChain {
    void doFilter(SimpleRequest request, SimpleResponse response);
}