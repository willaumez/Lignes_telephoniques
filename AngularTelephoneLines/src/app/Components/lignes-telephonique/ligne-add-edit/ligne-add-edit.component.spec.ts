import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LigneAddEditComponent } from './ligne-add-edit.component';

describe('LigneAddEditComponent', () => {
  let component: LigneAddEditComponent;
  let fixture: ComponentFixture<LigneAddEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LigneAddEditComponent]
    });
    fixture = TestBed.createComponent(LigneAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
