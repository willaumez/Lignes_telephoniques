import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LignesTelephoniqueComponent } from './lignes-telephonique.component';

describe('LignesTelephoniqueComponent', () => {
  let component: LignesTelephoniqueComponent;
  let fixture: ComponentFixture<LignesTelephoniqueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
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
