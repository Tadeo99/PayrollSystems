import { NgModule } from '@angular/core';
import { BsFindByKeyPipe } from './find-by-key.pipe';

@NgModule({
    declarations: [
        BsFindByKeyPipe
    ],
    exports: [
        BsFindByKeyPipe
    ]
})
export class BsFindByKeyPipeModule {
}
