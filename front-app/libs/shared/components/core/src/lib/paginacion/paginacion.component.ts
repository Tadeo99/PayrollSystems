import { Component, EventEmitter, Input, Output, ViewEncapsulation } from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { bsAnimations } from '@ng-mf/shared/bs';
import { BasePagination } from '@ng-mf/shared/service/core';

@Component({
  selector: 'ng-mf-bs-paginacion',
  templateUrl: './paginacion.component.html',
  styleUrls: ['./paginacion.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: bsAnimations
})
export class PaginacionComponent {

  @Input()
  public isLoading = false;
  @Output()
  public changeEmitter = new EventEmitter();
  @Input()
  public paginator!: MatPaginator;
  @Input()
  public pagination!: BasePagination | null;

  constructor() {
    //
  }


  // Cuando se lance el evento click en la plantilla llamaremos a este método
  public lanzar(event: any) {
    // Usamos el método emit
    this.changeEmitter.emit({});
  }

}