import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LignesTelephoniqueComponent } from './lignes-telephonique.component';
import {MatDialogModule} from "@angular/material/dialog";

describe('LignesTelephoniqueComponent', () => {
  let component: LignesTelephoniqueComponent;
  let fixture: ComponentFixture<LignesTelephoniqueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MatDialogModule],
      declarations: [LignesTelephoniqueComponent]
    });
    fixture = TestBed.createComponent(LignesTelephoniqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
