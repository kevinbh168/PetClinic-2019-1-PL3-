package com.tecsup.petclinic.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tecsup.petclinic.domain.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OwnerServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(PetServiceTest.class);

	@Autowired
	private OwnerService ownerService;
	
	//@Test
	public void testFindByFirstName() {
		String FIRST_NAME = "Harold";
		Owner owner;
		List<Owner> owners = ownerService.findByFirstName(FIRST_NAME);
		owner = owners.get(0);
		
		Assert.assertEquals(FIRST_NAME, owner.getFirstName());
		logger.info("Si se encontro el usuario de nombre: '" +FIRST_NAME + "'");
	}
	
	//@Test
	public void testFindByLastName() {
		String LAST_NAME = "Black";
		Owner owner;
		List<Owner> owners = ownerService.findByLastName(LAST_NAME);
		owner = owners.get(0);
		
		Assert.assertEquals(LAST_NAME, owner.getLastName());
		logger.info("Se encontro el propietario de apellido: '" + LAST_NAME + "'");
	}
	
	//@Test
	public void testFindByCity() {
		String CITY = "Madison";
		int i = 0;
		//Owner owner;
		List<Owner> owners = ownerService.findByCity(CITY);
		//owner = owners.get(0);
		
		for(Owner owner: owners) {
			Assert.assertEquals(CITY, owner.getCity());
			i++;
		}
		logger.info("En la ciudad de '" + CITY + "' se encontraron "+ i + " propietarios");	
	}
	
	//Cada vez que se ejecuta nuevo ID
	//@Test
	public void testDelete() throws OwnerNotFoundException {
		Long ID = 13L;
		
		ownerService.delete(ID);
		
		try {
			Owner owner = ownerService.findById(ID);
		}catch (Exception e) {
			logger.info("Se ha eliminado el Porpietario con ID:  "+ ID);
		}
	}
	
	@Test
	public void testCreateAndFind() {
		String FIRST_NAME = "Kevin";
		String LAST_NAME = "Brena";
		String CITY = "Comas";
		
		Owner oneOwner = new Owner(FIRST_NAME, LAST_NAME, CITY);
		
		Owner ownerCreate = ownerService.create(oneOwner);
		
		try {
			Owner ownerCreateFound = ownerService.findById(ownerCreate.getId());
			logger.info("Propietario con ID: "+ ownerCreateFound.getId() + " creado.");
		}catch (OwnerNotFoundException e) {
			logger.info("Propietario no creado.");
		}
		
		Iterable<Owner> ownersI = ownerService.findAll();
		
		while(ownersI.iterator().hasNext()) {
			try {
				Owner ownerInListFound = ownerService.findById(ownerCreate.getId());
				logger.info("Propietario con el ID: "+ ownerInListFound.getId() + " existe");
				break;
			}catch (OwnerNotFoundException e) {
				logger.info("Propietario no existe");
			}
		}
	}

}
