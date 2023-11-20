// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  isMicroServicio:false,
  apiHttp:'http://',
  apiIp:'localhost',
  apiPort:'8085',//8060;8085
  apiServletDescarga:'DescargarReporte',
  apiUrlConexto: 'apisiaa',
  service_key:'3b91cab8-926f-49b6-ba00-920bcf934c2a',
  version:'1.0',
  config: {
    "API_URL": "",
    "VAPID_PUBLIC_KEY": "BHe82datFpiOOT0k3D4pieGt1GU-xx8brPjBj0b22gvmwl-HLD1vBOP1AxlDKtwYUQiS9S-SDVGYe_TdZrYJLw8"
  },
  apiServicio:{
    "API_URL_AAS":"http://localhost:8080/aas/api/",
    "API_URL_COMMON":"http://localhost:8080/common/api/",
    "API_URL_SECURITY":"http://localhost:8080/security/api/",
    "WS_URL_CONSUMER":"ws://localhost:8080/consumer/",
  
    "API_URL_RRHH_ESCALAFON":"http://localhost:8080/rrhh/escalafon/api/",
    "API_URL_RRHH_PLANILLA":"http://localhost:8080/rrhh/planilla/api/",
    "API_URL_REPORTE":"http://localhost:8080/rrhh/api/",
    "API_URL_ADMISION":"http://localhost:8080/admision/api/",
    "API_URL_MATRICULA":"http://localhost:8080/matricula/api/",
    "API_URL_NOTA":"http://localhost:8080/nota/api/",
    "API_URL_PAGO":"http://localhost:8080/pago/api/",
    "API_URL_GENERADOR":"http://localhost:8080/generador/api/"
    
  },
  apiServicioDescarga: {
    "API_URL_DESCARGA_COMMON":"http://localhost:8080/rrhh/DescargarReporte",
    "API_URL_DESCARGA_RRHH_ESCALAFON":"http://localhost:8080/rrhh/DescargarReporte",
    "API_URL_DESCARGA_RRHH_PLANILLA":"http://localhost:8080/rrhh/planilla/DescargarReporte",
    "API_URL_DESCARGA_ADMISION":"http://localhost:8080/admision/DescargarReporte",
    "API_URL_DESCARGA_MATRICULA":"http://localhost:8080/matricula/DescargarReporte",
    "API_URL_DESCARGA_NOTA":"http://localhost:8080/nota/DescargarReporte",
    "API_URL_DESCARGA_PAGO":"http://localhost:8080/pago/DescargarReporte",
    "API_URL_DESCARGA_REPORTE":"http://localhost:8080/rrhh/DescargarReporte"
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
