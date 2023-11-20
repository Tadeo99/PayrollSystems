import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatPaginatorModule } from '@angular/material/paginator';
import { PaginacionComponent } from './paginacion.component';

@NgModule({
    declarations: [
        PaginacionComponent
    ],
    imports: [
        CommonModule,
        MatPaginatorModule
    ],
    exports: [
        PaginacionComponent
    ]
})
export class BsPaginacionModule {
}
