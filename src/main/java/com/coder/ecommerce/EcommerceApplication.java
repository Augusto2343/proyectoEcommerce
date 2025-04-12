package com.coder.ecommerce;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.Products;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(EcommerceApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		try {
			//Clientes
			Client cliente1 = new Client("Capitan","Noriega",12230345);
			Client cliente2 = new Client("Yako","Fidulais",12230335);
			//Invoices
			Invoice invoice1 = new Invoice("7/12/2024",3003);
			Invoice invoice2 = new Invoice("23/3/2025",39939);
			Invoice invoice3 = new Invoice("20/3/2025",39939);
			//Productos
			Products product1 = new Products("product1","AC130",10,10);

			Products product2 = new Products("product2","AC1304",10,14);
			Products product3 = new Products("product3","AC1303",1011,1);
			Products product4 = new Products("product4","AC1306",131,4);
			Products product5 = new Products("product5","DA1306",50,20);

			//Invoice_details
			InvoiceDetails detalleProd = new InvoiceDetails(3,30);
			InvoiceDetails detalleProd2 = new InvoiceDetails(6,60);
			InvoiceDetails detalleProd3 = new InvoiceDetails(1,1011);
			InvoiceDetails detalleProd4 = new InvoiceDetails(2,262);
			InvoiceDetails detalleProd5 = new InvoiceDetails(3,150);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
