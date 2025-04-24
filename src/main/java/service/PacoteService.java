package service;

import db.PacoteDAO;
import model.PacoteViagem;
import javax.swing.*;
import java.util.List;

public class PacoteService {

    private PacoteDAO pacoteDAO;

    public PacoteService(PacoteDAO pacoteDAO) {
        this.pacoteDAO = pacoteDAO;
    }



    public List<PacoteViagem> listarPacotes() {
        return pacoteDAO.listar();
    }
}
