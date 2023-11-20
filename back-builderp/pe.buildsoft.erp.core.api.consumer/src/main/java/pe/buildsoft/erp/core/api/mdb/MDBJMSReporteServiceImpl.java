package pe.buildsoft.erp.core.api.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

import pe.buildsoft.erp.core.domain.util.ConfiguracionJMSUtil;

/**
 * La Class MDBJMSReporteServiceImpl.
 * <ul>
 * <li>Copyright 2014 MAPFRE - mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 19:20:13 COT 2014
 * @since PWR v1.0
 */
@MessageDriven(name = "MDBJMSReporteServiceImpl", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = ConfiguracionJMSUtil.QUEUE_NAME),
		@ActivationConfigProperty(propertyName = "transactionTimeout", propertyValue = ConfiguracionJMSUtil.TRANSACCTION_TIMEOUT),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class MDBJMSReporteServiceImpl extends MDBJMSReporteServiceImplUtil implements MessageListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see jakarta.jms.MessageListener#onMessage(jakarta.jms.Message)
	 */
	@Override
	public void onMessage(Message aMessage) {
		super.onMessage(aMessage, ConfiguracionJMSUtil.QUEUE_NAME);
	}

}