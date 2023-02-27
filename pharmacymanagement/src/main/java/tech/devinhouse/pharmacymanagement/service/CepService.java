package tech.devinhouse.pharmacymanagement.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tech.devinhouse.pharmacymanagement.controller.dto.EnderecoResponse;

@FeignClient(url="viacep.com.br/ws/", name = "viacep-api")
public interface CepService {

    @GetMapping("{cep}/json/")
    EnderecoResponse buscaCep(@PathVariable("cep") String cep);

}
