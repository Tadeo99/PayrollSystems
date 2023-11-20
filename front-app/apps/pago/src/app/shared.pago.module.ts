import { NgModule ,ModuleWithProviders } from '@angular/core';

import { CatalogoCuentaComponent } from './catalogocuenta';
import { ClasificacionComponent } from './clasificacion/clasificacion.component';
import { CuentaBancariaEntidadComponent } from './cuentabancariaentidad/cuentabancariaentidad.component';
import { CuotaConceptoComponent } from './cuotaconcepto/cuotaconcepto.component';
import { EmpresaComponent } from './empresa/empresa.component';
import { TipoDocSunatEntidadComponent } from './tipodocsunatentidad';
import { SharedModule } from '@ng-mf/shared/components/core';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
   CatalogoCuentaComponent,
   CuotaConceptoComponent,  
   EmpresaComponent, 
   CuentaBancariaEntidadComponent,
   ClasificacionComponent,
   TipoDocSunatEntidadComponent,
  ],
  exports: [
    CatalogoCuentaComponent,
    CuotaConceptoComponent,
    EmpresaComponent,
    CuentaBancariaEntidadComponent,
    ClasificacionComponent,
    TipoDocSunatEntidadComponent,
  ],
  providers: [
 
  ]
})
export class SharedPagoModule { 

  static forRoot() {
    return {
      ngModule: SharedPagoModule,
      providers: []
    };
  }
}

 