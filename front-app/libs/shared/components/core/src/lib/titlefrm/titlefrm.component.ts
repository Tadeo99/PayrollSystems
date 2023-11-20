import {
  Component, Input, ViewEncapsulation
} from '@angular/core';
import { bsAnimations } from '@ng-mf/shared/bs';

@Component({
  selector: 'ng-mf-bs-titlefrm',
  templateUrl: './titlefrm.component.html',
  styleUrls: ['./titlefrm.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: bsAnimations
})
export class TitleFrmComponent {

  @Input()
  public accionNuevo = false;

  constructor() {
    //
  }

}