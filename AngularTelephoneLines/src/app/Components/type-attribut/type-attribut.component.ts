import { Component } from '@angular/core';

@Component({
  selector: 'app-type-attribut',
  templateUrl: './type-attribut.component.html',
  styleUrls: ['./type-attribut.component.scss']
})
export class TypeAttributComponent {

  selectedTabIndex: number = 0;

  selectTab(index: number):void {
    this.selectedTabIndex = index;
  }

}
