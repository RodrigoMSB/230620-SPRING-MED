package co.netec.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.netec.app.dao.IAlumnoDAO;
import co.netec.app.entities.Alumno;

@Service
public class AlumnoServiceImpl implements IAlumnoService {

	@Autowired
	private IAlumnoDAO dao;
	
	@Override
	@Transactional
	public Alumno save(Alumno alum) {
		
		Alumno alumno = dao.save(alum);
		
		return alumno;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findAll() {
		
		List<Alumno> listaAlumnos = (List<Alumno>)dao.findAll();
		
		return listaAlumnos;
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		
		// Aquí, hau que poner un try catch 
		dao.deleteById(id);
		
		// Aberración que se corregirá en el próximo ejercicio
		return true;
	}

	@Override
	@Transactional
	public Alumno update(Alumno alumno) {
		
		Optional<Alumno> alumnoBD = dao.findById(alumno.getId());
		
		if (alumnoBD.isPresent()) {
			
			Alumno alumnoActualizado = alumnoBD.get();
			
			alumnoActualizado.setNombre(alumno.getNombre());
			alumnoActualizado.setEdad(alumno.getEdad());
			alumnoActualizado.setCorreo(alumno.getCorreo());
			
			// Se está haciendo realmente un update, pero sin que lo veamos
			return save(alumnoActualizado);
			
		}
		
		
		return null;
	}

}
