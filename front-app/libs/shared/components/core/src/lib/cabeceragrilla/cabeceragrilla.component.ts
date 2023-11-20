import {
  Component, EventEmitter, Input, Output, ViewEncapsulation
} from '@angular/core';
import { UntypedFormControl } from '@angular/forms';
import { bsAnimations } from '@ng-mf/shared/bs';

@Component({
  selector: 'ng-mf-bs-cabeceragrilla',
  templateUrl: './cabeceragrilla.component.html',
  styleUrls: ['./cabeceragrilla.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: bsAnimations
})
export class CabeceraGrillaComponent {

  @Input()
  public titlePage = '';

  @Input()
  public isLoading = false;

  @Input()
  public searchInputControl = new UntypedFormControl();

  @Input()
  public isAdd = true;

  @Input()
  public isSearch = true;

  @Input()
  public keyButtonLabel = 'add';

  @Input()
  public invalid = false;

  @Input()
  public disabled = false;

  
  @Input()
  public showBotonNuevo = true;

  @Output()
  changeEmitter = new EventEmitter();

  constructor() {
    //
  }

  public regresar() {
    this.changeEmitter.emit({ isNuevo: false });
  }

  public nuevo() {
    this.changeEmitter.emit({ isNuevo: true });
  }

  // Cuando se lance el evento click en la plantilla llamaremos a este método
  public lanzar(event: any) {
    // Usamos el método emit
    this.changeEmitter.emit({});
  }
}