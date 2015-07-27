package co.gov.fonada.planeacion.mb;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import co.gov.fonada.planeacion.dao.*;
import co.gov.fonada.planeacion.model.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import org.mozilla.universalchardet.UniversalDetector;
import org.primefaces.model.UploadedFile;
import com.opencsv.CSVReader;

@ManagedBean
@ViewScoped
public class UploaderMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
//    private EtapaMB etapaMB = new EtapaMB();
    //    private ContratoMB contratoMB = new ContratoMB();
//    private ListaMB listaMB = new ListaMB();
//    private DivipolaMB divipolaMB = new DivipolaMB();
//    private PagoMB pagoMB = new PagoMB();


    private UploadedFile uploadFile;
    private Integer vigencia;

    public UploaderMB() {
        System.out.println("UploaderMB");
    }

    public UploadedFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadedFile uploadFile) {
        this.uploadFile = uploadFile;
    }


//    public void handleFileUpload(FileUploadEvent event) {
//        uploadFile = event.getFile();
//    }
//
//    public void handleCSVUpload() {
//        System.out.println("UploaderMB handleCSVUpload start");
//        System.out.println("UploaderMB handleCSVUpload size " + uploadFile.getSize());
//        System.out.println("UploaderMB handleCSVUpload contents ");
//    }

    public void etapaUpload() {
        EtapaMB etapaMB = new EtapaMB();

        System.out.println("UploaderMB etapaUpload size " + uploadFile.getSize());

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB etapaUpload encoding " + encoding);
            detector.reset();
//            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 0);
            EtapaDAO etapaDAO = etapaMB.getDao();
            etapaDAO.deleteAllEtapa();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 4) {
                    Etapa etapaTemp = new Etapa();
                    Key<Etapa> key = f.allocateId(Etapa.class);
                    try {
                        System.out.println("UploaderMB etapaUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);

                        etapaTemp.setId(key.getId());
                        etapaTemp.setOpcion(nextLine[0]);
                        etapaTemp.setModalidad(nextLine[1]);
                        etapaTemp.setEtapas(nextLine[2]);
                        etapaTemp.setDuracion(Integer.parseInt(nextLine[3]));

                        if (etapaDAO.save(etapaTemp) != null) count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            FacesContext context = FacesContext.getCurrentInstance();
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void fuenteUpload() {
        FuenteMB fuenteMB = new FuenteMB();

        System.out.println("UploaderMB fuenteUpload size " + uploadFile.getSize());

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB fuenteUpload encoding " + encoding);
            detector.reset();
//            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 0);
            FuenteDAO fuenteDAO = fuenteMB.getDao();
            fuenteDAO.deleteAllFuente();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 4) {
                    Fuente fuenteTemp = new Fuente();
                    Key<Fuente> key = f.allocateId(Fuente.class);
                    try {
                        System.out.println("UploaderMB fuenteUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);

                        fuenteTemp.setId(key.getId());
                        fuenteTemp.setFuente(nextLine[0]);
                        if (fuenteDAO.save(fuenteTemp) != null) count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            FacesContext context = FacesContext.getCurrentInstance();
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void contratoUpload() {
        ListaDAO listaDAO = new ListaDAOObjectify();
        EtapaDAO etapaDAO = new EtapaDAOObjectify();
//        ListaMB listaMB = new ListaMB();
//        EtapaMB etapaMB = new EtapaMB();
        System.out.println("UploaderMB contratoUpload size " + uploadFile.getSize());
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB contratoUpload encoding " + encoding);
            detector.reset();
            /*Formato definido para lsa fechas*/
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 1);
            ContratoDAO contratoDAO = new ContratoDAOObjectify();
//            contratoDAO.deleteAllContrato();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            Integer allCount = 1;
            System.out.println("UploaderMB contratoUpload iterating");
            Boolean isOK;
            List<Contrato> contratosTemp = new ArrayList<>();
            while ((nextLine = reader.readNext()) != null) {
//                System.out.println("UploaderMB contratoUpload key "+nextLine.toString());
                isOK = Boolean.TRUE;
                allCount++;
                if (nextLine.length == 9) {
                    Contrato contratoTemp;
                    contratoTemp = new Contrato();
/*
                    System.out.println("UploaderMB contratoUpload " + nextLine[0] + " | " + nextLine[1] + " | " + nextLine[2] + " | " + nextLine[3]+ " | " + nextLine[4]+ " | " + nextLine[5]+ " | " + nextLine[6]+ " | " + nextLine[7]+ " | " + nextLine[8]);
*/

                    try {
//                        System.out.println("UploaderMB contratoUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);
                        Long idTemp;
                        idTemp = Long.parseLong(nextLine[0]);
                        if (idTemp == 0) {
                            Key<Contrato> key = f.allocateId(Contrato.class);
                            contratoTemp.setId(key.getId());

                        } else {
                            try {
                                contratoTemp = contratoDAO.findById(idTemp);
                            } catch (Exception exe) {
                                isOK = Boolean.FALSE;
                                FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, allCount + " " + "Id incorrecto", nextLine[0] + " no existe. Use 0 para crear un nuevo ID.");
                                context.addMessage(null, messages);
                            }
                        }

                        if (!listaDAO.isInFilter("sector", "valor", nextLine[1])) {
                            //      System.out.println("UploaderMB contratoUpload Sector " + nextLine[0] + " incorrecto");
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, allCount + " " + "Sector incorrecto", nextLine[1] + " no existe.");
                            context.addMessage(null, messages);
                        }

                        if (!listaDAO.isInFilter("postulacion", "postulacion", nextLine[2])) {
                            //         System.out.println("UploaderMB contratoUpload Postulacion " + nextLine[1] + " incorrecto");
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, allCount + " " + "Postulación incorrecta", nextLine[2] + " no existe.");
                            context.addMessage(null, messages);
                        }
                        if (!etapaDAO.isInFilter("modalidad", nextLine[5])) {
                            //   System.out.println("UploaderMB contratoUpload Modalidad" + nextLine[4] + " incorrecto");
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, allCount + " " + "Modalidad incorrecta", nextLine[5] + " no existe.");
                            context.addMessage(null, messages);
                        }

                        if (!etapaDAO.isInFilter("opcion", nextLine[6])) {
                            //  System.out.println("UploaderMB contratoUpload Opcion" + nextLine[5] + " incorrecto");
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, allCount + " " + "Referencia incorrecta", nextLine[6] + " no existe.");
                            context.addMessage(null, messages);
                        }
                        if (isOK) {
                            contratoTemp.setSector(nextLine[1]);
                            contratoTemp.setPostulacion(nextLine[2]);
                            contratoTemp.setCodigoContrato(nextLine[3]);
                            contratoTemp.setDuracion(Double.parseDouble(nextLine[4]));
                            contratoTemp.setModalidad(nextLine[5]);
                            contratoTemp.setReferencia(nextLine[6]);
                            contratoTemp.setFechaRef(formatter.parse(nextLine[7]));
                            contratoTemp.setDescripcion(nextLine[8]);
                            contratosTemp.add(contratoTemp);
                            count++;
                            //System.out.println("UploaderMB contratoUpload count " + count);
                        } else {
                            System.out.println("UploaderMB contratoUpload " + allCount + "|" + nextLine[0] + "|" + nextLine[1] + "|" + nextLine[2] + "|" + nextLine[3] + "|" + nextLine[4] + "|" + nextLine[5] + "|" + nextLine[6] + "|" + nextLine[7] + "|" + nextLine[8]);
                        }
                    } catch (Exception e) {
//                        e.printStackTrace();
                        FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
                        context.addMessage(null, messages);
                    }
                }
            }
            contratoDAO.bulkSave(contratosTemp);
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void seguimientoUpload() {
        HitoDAO hitoDAO = new HitoDAOObjectify();
        SeguimientoDAO seguimientoDAO = new SeguimientoDAOObjectify();
        ContratoDAO contratoDAO = new ContratoDAOObjectify();

        System.out.println("UploaderMB contratoUpload size " + uploadFile.getSize());
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB contratoUpload encoding " + encoding);
            detector.reset();
            /*Formato definido para lsa fechas*/
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 1);
//            contratoDAO.deleteAllContrato();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            Integer allCount = 1;
            System.out.println("UploaderMB contratoUpload iterating");
            Boolean isOK;
            Key<Seguimiento> keySeguimiento = f.allocateId(Seguimiento.class);
            List<Hito> hitosTemp = new ArrayList<>();
            while ((nextLine = reader.readNext()) != null) {
//                System.out.println("UploaderMB hitoUpload key "+nextLine.toString());
                isOK = Boolean.TRUE;
                allCount++;
                if (nextLine.length == 3) {
                    Hito hitoTemp;
                    hitoTemp = new Hito();
//                    System.out.println("UploaderMB hitoUpload " + nextLine[0] + " | " + nextLine[1] + " | " + nextLine[2] + " | " + nextLine[3]+ " | " + nextLine[4]+ " | " + nextLine[5]+ " | " + nextLine[6]+ " | " + nextLine[7]+ " | " + nextLine[8]);
                    Long contratoId = 0L;
                    try {
//                        System.out.println("UploaderMB hitoUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);
                        Long idTemp;
                        idTemp = Long.parseLong(nextLine[0]);
                        Key<Hito> key = f.allocateId(Hito.class);
                        hitoTemp.setId(key.getId());

                        try {
                            contratoId = Long.parseLong(nextLine[0]);
                            isOK = contratoDAO.isContrato(contratoId);
                        } catch (Exception exe) {
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, allCount + " " + "Id incorrecto", nextLine[0] + " no existe. Use 0 para crear un nuevo ID.");
                            context.addMessage(null, messages);
                        }


                        if (isOK) {
                            hitoTemp.setContrato(contratoId);

                            hitoTemp.setTCCpub(formatter.parse(nextLine[1]));
                            hitoTemp.setContratoSig(formatter.parse(nextLine[2]));
                            hitoTemp.setParent(keySeguimiento);
                            hitosTemp.add(hitoTemp);
                            count++;
                            //System.out.println("UploaderMB hitoUpload count " + count);
                        } else {
                            System.out.println("UploaderMB hitoUpload " + allCount + "|" + nextLine[0] + "|" + nextLine[1] + "|" + nextLine[2]);
                        }
                    } catch (Exception e) {
//                        e.printStackTrace();
                        FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
                        context.addMessage(null, messages);
                    }
                }
            }
            hitoDAO.bulkSave(hitosTemp);
            if (count > 0) {
                Seguimiento seguimientoTemp = new Seguimiento();
                seguimientoTemp.setId(keySeguimiento.getId());
                seguimientoTemp.setTimeStamp(new Date());
                seguimientoDAO.save(seguimientoTemp);
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void productosPSAUpload() {
        ProductoPSADAO productoPSADAO = new ProductoPSADAOObjectify();
        ProductosPSADAO productosPSADAO = new ProductosPSADAOObjectify();
        ContratoDAO contratoDAO = new ContratoDAOObjectify();
        ListaDAO listaDAO = new ListaDAOObjectify();
        DivipolaDAO divipolaDAO = new DivipolaDAOObjectify();


        System.out.println("UploaderMB contratoUpload size " + uploadFile.getSize());
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB contratoUpload encoding " + encoding);
            detector.reset();
            /*Formato definido para lsa fechas*/
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 1);
//            contratoDAO.deleteAllContrato();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            Integer allCount = 1;
            System.out.println("UploaderMB contratoUpload iterating");
            Boolean isOK;
            Key<ProductosPSA> keyProductosPSA = f.allocateId(ProductosPSA.class);
            List<ProductoPSA> productoPSATempList = new ArrayList<>();
            while ((nextLine = reader.readNext()) != null) {
//                System.out.println("UploaderMB productoPSAUpload key "+nextLine.toString());
                isOK = Boolean.TRUE;
                allCount++;
                if (nextLine.length == 16) {
                    ProductoPSA productoPSATemp;
                    productoPSATemp = new ProductoPSA();
//                    System.out.println("UploaderMB productoPSAUpload " + nextLine[0] + " | " + nextLine[1] + " | " + nextLine[2] + " | " + nextLine[3]+ " | " + nextLine[4]+ " | " + nextLine[5]+ " | " + nextLine[6]+ " | " + nextLine[7]+ " | " + nextLine[8]);
                    Long contratoId = 0L;
                    try {
//                        System.out.println("UploaderMB productoPSAUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);
                        Key<ProductoPSA> key = f.allocateId(ProductoPSA.class);
                        productoPSATemp.setId(key.getId());

                        try {
                            contratoId = Long.parseLong(nextLine[0]);
                            isOK = contratoDAO.isContrato(contratoId);
                        } catch (Exception exe) {
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, allCount + " " + "Id incorrecto", nextLine[0] + " no existe. Use 0 para crear un nuevo ID.");
                            context.addMessage(null, messages);
                        }
                        if (!listaDAO.isInFilter("postulacion", "intervencion", nextLine[1])) {

                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, "Intervención incorrecta", nextLine[1] + " no existe.");
                            context.addMessage(null, messages);
                        }
                        if (!listaDAO.isInFilter("producto", "productoPSA", nextLine[2])) {

                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto incorrecto", nextLine[2] + " no existe.");
                            context.addMessage(null, messages);
                        }
                        if (!divipolaDAO.isDivipola(Integer.parseInt(nextLine[3]))) {
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, "Divipola incorrecta", nextLine[3] + " no existe.");
                            context.addMessage(null, messages);
                        }
                        if (isOK) {
                            productoPSATemp.setContrato(contratoId);

                            productoPSATemp.setIntervencion(nextLine[1]);
                            productoPSATemp.setProducto(nextLine[2]);
                            productoPSATemp.setDivipola(Integer.parseInt(nextLine[3]));
                            productoPSATemp.setEnero(Float.parseFloat(nextLine[4]));
                            productoPSATemp.setFebrero(Float.parseFloat(nextLine[5]));
                            productoPSATemp.setMarzo(Float.parseFloat(nextLine[6]));
                            productoPSATemp.setAbril(Float.parseFloat(nextLine[7]));
                            productoPSATemp.setMayo(Float.parseFloat(nextLine[8]));
                            productoPSATemp.setJunio(Float.parseFloat(nextLine[9]));
                            productoPSATemp.setJulio(Float.parseFloat(nextLine[10]));
                            productoPSATemp.setAgosto(Float.parseFloat(nextLine[11]));
                            productoPSATemp.setSeptiembre(Float.parseFloat(nextLine[12]));
                            productoPSATemp.setOctubre(Float.parseFloat(nextLine[13]));
                            productoPSATemp.setNoviembre(Float.parseFloat(nextLine[14]));
                            productoPSATemp.setDiciembre(Float.parseFloat(nextLine[15]));
                            productoPSATemp.setParent(keyProductosPSA);
                            productoPSATempList.add(productoPSATemp);
                            count++;
                            //System.out.println("UploaderMB productoPSAUpload count " + count);
                        } else {
                            System.out.println("UploaderMB productoPSAUpload " + allCount + "|" + nextLine[0] + "|" + nextLine[1] + "|" + nextLine[2]);
                        }
                    } catch (Exception e) {
//                        e.printStackTrace();
                        FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
                        context.addMessage(null, messages);
                    }
                }
            }
            productoPSADAO.bulkSave(productoPSATempList);
            if (count > 0) {
                ProductosPSA productosPSATemp = new ProductosPSA();
                productosPSATemp.setId(keyProductosPSA.getId());
                productosPSATemp.setTimeStamp(new Date());
                productosPSATemp.setVigencia(vigencia);
                productosPSADAO.save(productosPSATemp);
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void pagoUpload() {
        System.out.println("UploaderMB pagoUpload size " + uploadFile.getSize());
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB pagoUpload encoding " + encoding);
            detector.reset();
            CSVReader reader;
            if (encoding == null) {
                FacesMessage msg = new FacesMessage("Advertencia", "Forzando windows-1252");
                context.addMessage(null, msg);
                reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), Charset.forName("windows-1252")), '|', '"', 1);

            } else {
                reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 1);

            }
            PagoDAO pagoDAO = new PagoDAOObjectify();
            pagoDAO.deleteAllPago();

//            pagoDAO.deleteAllPago();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            Integer pack = 0;
            List<Pago> pagosTemp = new ArrayList<>();
            System.out.println("UploaderMB pagoUpload iterating");
            while ((nextLine = reader.readNext()) != null) {
//                System.out.println("UploaderMB pagoUpload key "+nextLine.toString());
                if (nextLine.length == 55) {
                    Pago pagoTemp = new Pago();
                    Key<Pago> key = f.allocateId(Pago.class);
//                   System.out.println("UploaderMB pagoUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);

                    try {
//                        System.out.println("UploaderMB pagoUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);

                        pagoTemp.setId(key.getId());
                        pagoTemp.setParent(Key.create(Contrato.class, Long.parseLong(nextLine[0])));
                        pagoTemp.setFuente(nextLine[1]);
                        pagoTemp.setAporte(Float.parseFloat(nextLine[2]));
                        pagoTemp.setVigencia2015(Float.parseFloat(nextLine[3]));
                        pagoTemp.setVigencia2016(Float.parseFloat(nextLine[4]));
                        pagoTemp.setVigencia2017(Float.parseFloat(nextLine[5]));
                        pagoTemp.setVigencia2018(Float.parseFloat(nextLine[6]));
                        pagoTemp.setEnero2015(Float.parseFloat(nextLine[7]));
                        pagoTemp.setFebrero2015(Float.parseFloat(nextLine[8]));
                        pagoTemp.setMarzo2015(Float.parseFloat(nextLine[9]));
                        pagoTemp.setAbril2015(Float.parseFloat(nextLine[10]));
                        pagoTemp.setMayo2015(Float.parseFloat(nextLine[11]));
                        pagoTemp.setJunio2015(Float.parseFloat(nextLine[12]));
                        pagoTemp.setJulio2015(Float.parseFloat(nextLine[13]));
                        pagoTemp.setAgosto2015(Float.parseFloat(nextLine[14]));
                        pagoTemp.setSeptiembre2015(Float.parseFloat(nextLine[15]));
                        pagoTemp.setOctubre2015(Float.parseFloat(nextLine[16]));
                        pagoTemp.setNoviembre2015(Float.parseFloat(nextLine[17]));
                        pagoTemp.setDiciembre2015(Float.parseFloat(nextLine[18]));
                        pagoTemp.setEnero2016(Float.parseFloat(nextLine[19]));
                        pagoTemp.setFebrero2016(Float.parseFloat(nextLine[20]));
                        pagoTemp.setMarzo2016(Float.parseFloat(nextLine[21]));
                        pagoTemp.setAbril2016(Float.parseFloat(nextLine[22]));
                        pagoTemp.setMayo2016(Float.parseFloat(nextLine[23]));
                        pagoTemp.setJunio2016(Float.parseFloat(nextLine[24]));
                        pagoTemp.setJulio2016(Float.parseFloat(nextLine[25]));
                        pagoTemp.setAgosto2016(Float.parseFloat(nextLine[26]));
                        pagoTemp.setSeptiembre2016(Float.parseFloat(nextLine[27]));
                        pagoTemp.setOctubre2016(Float.parseFloat(nextLine[28]));
                        pagoTemp.setNoviembre2016(Float.parseFloat(nextLine[29]));
                        pagoTemp.setDiciembre2016(Float.parseFloat(nextLine[30]));
                        pagoTemp.setEnero2017(Float.parseFloat(nextLine[31]));
                        pagoTemp.setFebrero2017(Float.parseFloat(nextLine[32]));
                        pagoTemp.setMarzo2017(Float.parseFloat(nextLine[33]));
                        pagoTemp.setAbril2017(Float.parseFloat(nextLine[34]));
                        pagoTemp.setMayo2017(Float.parseFloat(nextLine[35]));
                        pagoTemp.setJunio2017(Float.parseFloat(nextLine[36]));
                        pagoTemp.setJulio2017(Float.parseFloat(nextLine[37]));
                        pagoTemp.setAgosto2017(Float.parseFloat(nextLine[38]));
                        pagoTemp.setSeptiembre2017(Float.parseFloat(nextLine[39]));
                        pagoTemp.setOctubre2017(Float.parseFloat(nextLine[40]));
                        pagoTemp.setNoviembre2017(Float.parseFloat(nextLine[41]));
                        pagoTemp.setDiciembre2017(Float.parseFloat(nextLine[42]));
                        pagoTemp.setEnero2018(Float.parseFloat(nextLine[43]));
                        pagoTemp.setFebrero2018(Float.parseFloat(nextLine[44]));
                        pagoTemp.setMarzo2018(Float.parseFloat(nextLine[45]));
                        pagoTemp.setAbril2018(Float.parseFloat(nextLine[46]));
                        pagoTemp.setMayo2018(Float.parseFloat(nextLine[47]));
                        pagoTemp.setJunio2018(Float.parseFloat(nextLine[48]));
                        pagoTemp.setJulio2018(Float.parseFloat(nextLine[49]));
                        pagoTemp.setAgosto2018(Float.parseFloat(nextLine[50]));
                        pagoTemp.setSeptiembre2018(Float.parseFloat(nextLine[51]));
                        pagoTemp.setOctubre2018(Float.parseFloat(nextLine[52]));
                        pagoTemp.setNoviembre2018(Float.parseFloat(nextLine[53]));
                        pagoTemp.setDiciembre2018(Float.parseFloat(nextLine[54]));

                        pagosTemp.add(pagoTemp);

//                        if (pagoDAO.save(pagoTemp) != null)
                        count++;
/*                        pack++;
                        if (pack >= 100) {
                            pack = 0;
                            pagoDAO.bulkSave(pagosTemp);
                            pagosTemp.clear();
                        }*/
//                        System.out.println("UploaderMB pagoUpload count " + count);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("UploaderMB pagoUpload start bulkSave ");
            pagoDAO.bulkSave(pagosTemp);
            System.out.println("UploaderMB pagoUpload end bulkSave ");

            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void regionalizacionUpload() {
        DivipolaDAO divipolaDAO = new DivipolaDAOObjectify();
        System.out.println("UploaderMB regionalizacionUpload size " + uploadFile.getSize());
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB regionalizacionUpload encoding " + encoding);
            detector.reset();
            CSVReader reader;
            if (encoding == null) {
                FacesMessage msg = new FacesMessage("Advertencia", "Forzando windows-1252");
                context.addMessage(null, msg);
                reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), Charset.forName("windows-1252")), '|', '"', 1);
            } else {
                reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 1);
            }
            RegionalizacionDAO regionalizacionDAO = new RegionalizacionDAOObjectify();
            regionalizacionDAO.deleteAllRegionalizacion();

//            regionalizacionDAO.deleteAllRegionalizacion();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            System.out.println("UploaderMB regionalizacionUpload iterating");
            Boolean isOK;
            List<Regionalizacion> regionalizacionsTemp = new ArrayList<>();

            while ((nextLine = reader.readNext()) != null) {
//                System.out.println("UploaderMB regionalizacionUpload key "+nextLine.toString());
                isOK = Boolean.TRUE;
                if (nextLine.length == 4) {
                    Regionalizacion regionalizacionTemp = new Regionalizacion();
                    Key<Regionalizacion> key = f.allocateId(Regionalizacion.class);
//                    System.out.println("UploaderMB regionalizacionUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);

                    try {
//                        System.out.println("UploaderMB regionalizacionUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);
                        if (!divipolaDAO.isDivipola(Integer.parseInt(nextLine[1]))) {
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, "Divipola incorrecta", nextLine[4] + " no existe.");
                            context.addMessage(null, messages);
                        }
                        if (isOK) {
                            regionalizacionTemp.setId(key.getId());
                            regionalizacionTemp.setParent(Key.create(Contrato.class, Long.parseLong(nextLine[0])));
                            regionalizacionTemp.setDivipola(Integer.parseInt(nextLine[1]));
                            regionalizacionTemp.setValor(Float.parseFloat(nextLine[2]));
                            regionalizacionTemp.setBeneficiarios(Float.parseFloat(nextLine[3]));
                            regionalizacionsTemp.add(regionalizacionTemp);
                            count++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            regionalizacionDAO.bulkSave(regionalizacionsTemp);
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void accionUpload() {
        ListaDAO listaDAO = new ListaDAOObjectify();
        DivipolaDAO divipolaDAO = new DivipolaDAOObjectify();
        System.out.println("UploaderMB accionUpload size " + uploadFile.getSize());
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB accionUpload encoding " + encoding);
            detector.reset();
            CSVReader reader;
            if (encoding == null) {
                FacesMessage msg = new FacesMessage("Advertencia", "Forzando windows-1252");
                context.addMessage(null, msg);
                reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), Charset.forName("windows-1252")), '|', '"', 1);

            } else {
                reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 1);

            }
            AccionDAO accionDAO = new AccionDAOObjectify();
            accionDAO.deleteAllAccion();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            System.out.println("UploaderMB accionUpload iterating");
            Boolean isOK;
            List<Accion> accionsTemp = new ArrayList<>();

            while ((nextLine = reader.readNext()) != null) {
//                System.out.println("UploaderMB accionUpload key "+nextLine.toString());
                isOK = Boolean.TRUE;
                if (nextLine.length == 56) {
                    Accion accionTemp = new Accion();
                    Key<Accion> key = f.allocateId(Accion.class);
//                    System.out.println("UploaderMB accionUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);

                    try {
//                        System.out.println("UploaderMB accionUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);
                        if (!divipolaDAO.isDivipola(Integer.parseInt(nextLine[2]))) {
                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, "Divipola incorrecta", nextLine[4] + " no existe.");
                            context.addMessage(null, messages);
                        }
                        if (!listaDAO.isInFilter("postulacion", "intervencion", nextLine[3])) {

                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, "Intervención incorrecta", nextLine[4] + " no existe.");
                            context.addMessage(null, messages);
                        }
                        if (!listaDAO.isInFilter("producto", "productoPND", nextLine[1])) {

                            isOK = Boolean.FALSE;
                            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto incorrecta", nextLine[4] + " no existe.");
                            context.addMessage(null, messages);
                        }

                        if (isOK) {
                            accionTemp.setId(key.getId());
                            accionTemp.setParent(Key.create(Contrato.class, Long.parseLong(nextLine[0])));
                            accionTemp.setProducto(nextLine[1]);
                            accionTemp.setDivipola(Integer.parseInt(nextLine[2]));
                            accionTemp.setIntervencion((nextLine[3]));
                            accionTemp.setVigencia2015(Float.parseFloat(nextLine[4]));
                            accionTemp.setVigencia2016(Float.parseFloat(nextLine[5]));
                            accionTemp.setVigencia2017(Float.parseFloat(nextLine[6]));
                            accionTemp.setVigencia2018(Float.parseFloat(nextLine[7]));
                            accionTemp.setEnero2015(Float.parseFloat(nextLine[8]));
                            accionTemp.setFebrero2015(Float.parseFloat(nextLine[9]));
                            accionTemp.setMarzo2015(Float.parseFloat(nextLine[10]));
                            accionTemp.setAbril2015(Float.parseFloat(nextLine[11]));
                            accionTemp.setMayo2015(Float.parseFloat(nextLine[12]));
                            accionTemp.setJunio2015(Float.parseFloat(nextLine[13]));
                            accionTemp.setJulio2015(Float.parseFloat(nextLine[14]));
                            accionTemp.setAgosto2015(Float.parseFloat(nextLine[15]));
                            accionTemp.setSeptiembre2015(Float.parseFloat(nextLine[16]));
                            accionTemp.setOctubre2015(Float.parseFloat(nextLine[17]));
                            accionTemp.setNoviembre2015(Float.parseFloat(nextLine[18]));
                            accionTemp.setDiciembre2015(Float.parseFloat(nextLine[19]));
                            accionTemp.setEnero2016(Float.parseFloat(nextLine[20]));
                            accionTemp.setFebrero2016(Float.parseFloat(nextLine[21]));
                            accionTemp.setMarzo2016(Float.parseFloat(nextLine[22]));
                            accionTemp.setAbril2016(Float.parseFloat(nextLine[23]));
                            accionTemp.setMayo2016(Float.parseFloat(nextLine[24]));
                            accionTemp.setJunio2016(Float.parseFloat(nextLine[25]));
                            accionTemp.setJulio2016(Float.parseFloat(nextLine[26]));
                            accionTemp.setAgosto2016(Float.parseFloat(nextLine[27]));
                            accionTemp.setSeptiembre2016(Float.parseFloat(nextLine[28]));
                            accionTemp.setOctubre2016(Float.parseFloat(nextLine[29]));
                            accionTemp.setNoviembre2016(Float.parseFloat(nextLine[30]));
                            accionTemp.setDiciembre2016(Float.parseFloat(nextLine[31]));
                            accionTemp.setEnero2017(Float.parseFloat(nextLine[32]));
                            accionTemp.setFebrero2017(Float.parseFloat(nextLine[33]));
                            accionTemp.setMarzo2017(Float.parseFloat(nextLine[34]));
                            accionTemp.setAbril2017(Float.parseFloat(nextLine[35]));
                            accionTemp.setMayo2017(Float.parseFloat(nextLine[36]));
                            accionTemp.setJunio2017(Float.parseFloat(nextLine[37]));
                            accionTemp.setJulio2017(Float.parseFloat(nextLine[38]));
                            accionTemp.setAgosto2017(Float.parseFloat(nextLine[39]));
                            accionTemp.setSeptiembre2017(Float.parseFloat(nextLine[40]));
                            accionTemp.setOctubre2017(Float.parseFloat(nextLine[41]));
                            accionTemp.setNoviembre2017(Float.parseFloat(nextLine[42]));
                            accionTemp.setDiciembre2017(Float.parseFloat(nextLine[43]));
                            accionTemp.setEnero2018(Float.parseFloat(nextLine[44]));
                            accionTemp.setFebrero2018(Float.parseFloat(nextLine[45]));
                            accionTemp.setMarzo2018(Float.parseFloat(nextLine[46]));
                            accionTemp.setAbril2018(Float.parseFloat(nextLine[47]));
                            accionTemp.setMayo2018(Float.parseFloat(nextLine[48]));
                            accionTemp.setJunio2018(Float.parseFloat(nextLine[49]));
                            accionTemp.setJulio2018(Float.parseFloat(nextLine[50]));
                            accionTemp.setAgosto2018(Float.parseFloat(nextLine[51]));
                            accionTemp.setSeptiembre2018(Float.parseFloat(nextLine[52]));
                            accionTemp.setOctubre2018(Float.parseFloat(nextLine[53]));
                            accionTemp.setNoviembre2018(Float.parseFloat(nextLine[54]));
                            accionTemp.setDiciembre2018(Float.parseFloat(nextLine[55]));

                            accionsTemp.add(accionTemp);
                            count++;
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            accionDAO.bulkSave(accionsTemp);
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void divipolaUpload() {
        DivipolaMB divipolaMB = new DivipolaMB();
        System.out.println("UploaderMB divipolaUpload size " + uploadFile.getSize());

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB divipolaUpload encoding " + encoding);
            detector.reset();
//            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 0);
            DivipolaDAO divipolaDAO = divipolaMB.getDao();
            divipolaDAO.deleteAllDivipola();
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 4) {
                    Divipola divipolaTemp = new Divipola();
                    Key<Divipola> key = f.allocateId(Divipola.class);
                    try {
                        System.out.println("UploaderMB divipolaUpload " + nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);

                        divipolaTemp.setId(key.getId());
                        divipolaTemp.setEntidad(nextLine[0]);
                        divipolaTemp.setDepartamento(nextLine[1]);
                        divipolaTemp.setMunicipio(nextLine[2]);
                        divipolaTemp.setDivipola(Integer.parseInt(nextLine[3]));

                        if (divipolaDAO.save(divipolaTemp) != null) count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            FacesContext context = FacesContext.getCurrentInstance();
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados.");
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados.");
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void sectorUpload() {
        ListaDAO listaDAO = new ListaDAOObjectify();
        System.out.println("UploaderMB sectorUpload size " + uploadFile.getSize());

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB sectorUpload encoding " + encoding);
            detector.reset();
//          CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 0);
            listaDAO.deleteSomeLista("sector");
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            List<Lista> listasTemp = new ArrayList<>();
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 1) {
                    Lista listaTemp = new Lista();
                    Key<Lista> key = f.allocateId(Lista.class);
                    try {

//                        System.out.println("UploaderMB sectorUpload " + nextLine[0]);

                        listaTemp.setId(key.getId());
                        listaTemp.setList("sector");
                        listaTemp.setValor(nextLine[0]);
                        listasTemp.add(listaTemp);
                        count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            listaDAO.bulkSave(listasTemp);
            FacesContext context = FacesContext.getCurrentInstance();
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados. " + encoding);
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados. " + encoding);
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void postulacionUpload() {
        ListaDAO listaDAO = new ListaDAOObjectify();
        System.out.println("UploaderMB postulacionUpload size " + uploadFile.getSize());

        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB postulacionUpload encoding " + encoding);
            detector.reset();
//          CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 0);
            listaDAO.deleteSomeLista("postulacion");
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            Integer pack = 0;
            List<Lista> listasTemp = new ArrayList<>();


//            System.out.println("UploaderMB postulacionUpload reader.readAll().size() " + reader.readAll().size());
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 2) {
                    Lista listaTemp = new Lista();
                    Key<Lista> key = f.allocateId(Lista.class);
                    try {

//                        System.out.println("UploaderMB postulacionUpload " + nextLine[0]);

                        listaTemp.setId(key.getId());
                        listaTemp.setList("postulacion");
                        listaTemp.setPostulacion(nextLine[0]);
                        listaTemp.setIntervencion(nextLine[1]);
                        listasTemp.add(listaTemp);
                        count++;
/*                        pack++;
                        if (pack >= 100) {
                            pack = 0;
                            listaDAO.bulkSave(listasTemp);
                            listasTemp.clear();
                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            listaDAO.bulkSave(listasTemp);
            FacesContext context = FacesContext.getCurrentInstance();
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados. " + encoding);
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados. " + encoding);
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void productoUpload() {
        ListaDAO listaDAO = new ListaDAOObjectify();
        System.out.println("UploaderMB productoUpload size " + uploadFile.getSize());
        try {
            UniversalDetector detector = new UniversalDetector(null);
            byte[] uploadFileBytes = uploadFile.getContents();
            detector.handleData(uploadFileBytes, 0, uploadFileBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            System.out.println("UploaderMB productoUpload encoding " + encoding);
            detector.reset();
//          CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), StandardCharsets.ISO_8859_1), '|', '"', 0);
            CSVReader reader = new CSVReader(new InputStreamReader(uploadFile.getInputstream(), encoding), '|', '"', 0);
            listaDAO.deleteSomeLista("producto");
            String[] nextLine;
            ObjectifyFactory f = new ObjectifyFactory();
            Integer count = 0;
            List<Lista> listasTemp = new ArrayList<>();

//            System.out.println("UploaderMB productoUpload reader.readAll().size() " + reader.readAll().size());
            while ((nextLine = reader.readNext()) != null) {
                System.out.println("debug1");
                if (nextLine.length == 4) {
                    System.out.println("debug2");
                    System.out.println("UploaderMB productoUpload " + nextLine[0] + " " + nextLine[1]);

                    Lista listaTemp = new Lista();
                    Key<Lista> key = f.allocateId(Lista.class);
                    try {
                        listaTemp.setId(key.getId());
                        listaTemp.setList("producto");
                        listaTemp.setSector(nextLine[0]);
                        listaTemp.setPostulacion(nextLine[1]);
                        listaTemp.setProductoPND(nextLine[2]);
                        listaTemp.setProductoPSA(nextLine[3]);
                        listasTemp.add(listaTemp);
                        count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            listaDAO.bulkSave(listasTemp);
            FacesContext context = FacesContext.getCurrentInstance();
            if (count > 0) {
                FacesMessage msg = new FacesMessage("Carga exitosa", count + " registros cargados. " + encoding);
                context.addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Carga errónea", count + " registros cargados. " + encoding);
                context.addMessage(null, msg);
            }
            refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }

    public void editar() {
        System.out.println("UploaderMB editar");
    }


    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }
}