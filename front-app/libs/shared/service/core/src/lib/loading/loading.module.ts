import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BsLoadingInterceptor } from './loading.interceptor';

@NgModule({
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: BsLoadingInterceptor,
            multi: true
        }
    ]
})
export class BsLoadingModule {
}
