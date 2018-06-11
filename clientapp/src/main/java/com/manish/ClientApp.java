package com.manish;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.health.ServiceHealth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class ClientApp {

	public static void main(String[] args) {
		SpringApplication.run(ClientApp.class, args);
	}

	@RequestMapping("/")
	public String root() {
		return "Hello Docker of Client App";
	}

	@RequestMapping(method = {RequestMethod.GET}, value = {"/home"})
	public String home() {
		return "Hello from Client App.";
	}

	@RequestMapping(method = {RequestMethod.GET}, value = {"/home-remote"})
	public String homeService() {
		Consul consul = Consul.builder().
				withHostAndPort(HostAndPort.fromParts("172.18.1.101", 8500)).
				build(); // connect to Consul on localhost
		HealthClient healthClient = consul.healthClient();

		// discover only "passing" nodes
		List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("hello-service").getResponse();

		StringBuffer buffer = new StringBuffer("Consul Clients - ");
		for (ServiceHealth serviceHealth: nodes) {
			buffer.append("<BR/>");
			buffer.append(serviceHealth.getNode().getAddress());
		}

		buffer.append("<HR/> Service Discovered - ");
		for (ServiceHealth serviceHealth: nodes) {
			buffer.append("<BR/>");
			buffer.append(serviceHealth.getService().getAddress()+
					":" + serviceHealth.getService().getPort()+
					"/" + serviceHealth.getService().getTags().toString());
		}
		return buffer.toString();

	}
}
